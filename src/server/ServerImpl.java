package server;

import client.ClientInterface;
import model.Chat;
import model.FriendRequest;
import model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

	private final DataBase db;

	HashMap<Integer, ClientInterface> clients;

	protected ServerImpl() throws Exception {

		super();

		this.db = new DataBase();

		clients = new HashMap<>();

	}

	@Override
	public synchronized User login(ClientInterface client, String name, String password) throws RemoteException {

		// Obtenemos el usuario
		User user = db.getUserWithPassword(name, password);

		if (user == null) {
			return null;
		}

		// Si ese usuario ya esta conectado
		if(this.clients.get(user.hashCode()) != null) {
			return null;
		}

		// Obtenemos la lista de amigos de la base de datos
		ArrayList<User> userFriends = db.getFriends(user.getId());

		// Creamos un hashmap con los clientes que tenemos conectamos que sean amigos
		HashMap<User, ClientInterface> friendsClients = new HashMap<>();
		HashMap<User, Chat> chats = new HashMap<>();

		// Iteramos por la lista de amigos buscando los que esten conectados
		for (User friend : userFriends) {

			ClientInterface friendClient = this.clients.get(friend.hashCode());

			if (friendClient == null) {
				continue;
			}

			// Añadirmos el amigo conectado al hashmap del amigos conectados del propio cliente
			friendsClients.put(friend, friendClient);
			// Añadimos el chat con el amigo que está conectado
			chats.put(friend, new Chat());

			//Notificamos la conexion al amigo
			friendClient.notifyConnectedFriend(client, user);

		}

		// Obtenemos la lista de peticiones de amistad de la base de datos
		ArrayList<FriendRequest> friendRequests = db.getFriendRequest(user.getId());

		// Creamos el usuario con todos estos datos
		User finalUser = new User(user.getId(), user.getName(), friendsClients, friendRequests, chats);

		// Guardamos el nuevo usuario conectado
		this.clients.put(user.hashCode(), client);

		return finalUser;

	}

	// Comprueba que el cliente que hace la peticion es el correcto
	@Override
	public boolean validateRequest(ClientInterface client, String name, String password) throws RemoteException {
                System.out.println("ValidateRequest");
                System.out.println("name1: " + name);
                System.out.println("password1: "+ password);
		User user = this.db.getUserWithPassword(name, password);

		if (user == null) {
                        System.out.println("ValidateRequest: User = null");
			return false;

		}
                System.out.println("ValidateRequest: haschode.equals(): " + client.equals(this.clients.get(user.hashCode())));
		return client.equals(this.clients.get(user.hashCode()));

	}

	// Registra un usuario en la base de datos
	@Override
	public boolean register(String name, String password) throws RemoteException {

		return this.db.registerUser(name, password);

	}

	// Elimina al usuario que se desconecta de la lista de amigos
	// de los amigos que estan conectados
	@Override
	public synchronized void logout(ClientInterface client, User user) throws RemoteException {

		// Verificamos que el cliente que hace la peticion es el correcto
		ClientInterface value = this.clients.get(user.hashCode());
		if (client.equals(value)) {

			this.clients.remove(user.hashCode());

		}

		// Notificamos a los amigos conectados que el usuario se ha desconectado
		for (ClientInterface friendClient : user.getConnectedFriends().values()) {

			friendClient.notifyDisconnectedFriend(user);

		}

	}

	// 
	@Override
	public boolean requestFriend(ClientInterface client, String friendName, String name, String password) throws RemoteException {
		// Comprobamos que el cliente que hace la peticion es el correcto
		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}
		
		// Registramos la peticion de amistad en la base de datos
		FriendRequest request = this.db.registerFriendRequest(name, friendName);

		if (request == null) {
			return false;
		}

		// Obtenemos el usuario al que se le ha enviado la peticion de amistad
		User friend = this.db.getUser(friendName);

		// Comprobamos si el usuario esta conectado
		ClientInterface friendClient = this.clients.get(friend.hashCode());

		// Si esta conectado le notificamos la peticion de amistad
		if (friendClient != null) {

			friendClient.notifyFriendRequest(request);

		}

		return true;

	}

	@Override
	public boolean acceptFriendRequest(ClientInterface client, int requestId, String friendName, String name, String password) throws RemoteException {
		// Comprobamos que el cliente que hace la peticion es el correcto
		boolean valid = this.validateRequest(client, name, password);
		if (!valid) {
			return false;
		}

		// La base de datos acepta la petición de amistad
		boolean res = this.db.acceptFriendRequest(requestId, name, friendName);

		if (!res) {
			return false;
		}
		// Si se ha aceptado, almacenamos los dos nuevos amigos
		User me = this.db.getUser(name);
		User friend = this.db.getUser(friendName);

		// Seleccionamos el amigo de la forma en la que está almacenado
		// en la lista de clientes
		ClientInterface friendClient = this.clients.get(friend.hashCode());

		if (friendClient == null) {
			return true;
		}

		// Notificamos a ambos clientes que su respectivo amigo está
		// conectado
		client.notifyConnectedFriend(friendClient, friend);
		friendClient.notifyConnectedFriend(client, me);

		return true;

	}

	@Override
	public boolean declineFriendRequest(ClientInterface client, int requestId, String friendName, String name, String password) throws RemoteException {
		// Comprobamos si el request es valido
		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}
		// La base de datos rechaza la FriendRequest
		return this.db.declineFriendRequest(requestId, name, friendName);

	}

	@Override
	public boolean removeFriend(ClientInterface client, String friendName, String name, String password) throws RemoteException {
		// Comprobamos la request
		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}

		// La base de datos elimina al amigo
		return this.db.removeFriend(name, friendName);

	}

}