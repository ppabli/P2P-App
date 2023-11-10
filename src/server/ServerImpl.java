package src.server;

import src.client.ClientInterface;
import src.model.Chat;
import src.model.FriendRequest;
import src.model.User;

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

		// Creamos un array con los clientes que tenemos conectamos que sean amigos
		HashMap<User, ClientInterface> friendsClients = new HashMap<>();
		HashMap<User, Chat> chats = new HashMap<>();

		// Iteramos por la lista de amigos buscando los que esten conectados
		for (User friend : userFriends) {

			ClientInterface friendClient = this.clients.get(friend.hashCode());

			if (friendClient == null) {
				continue;
			}

			// Buscamos en los clientes conectados segun el nombre del amigo
			friendsClients.put(friend, friendClient);
			chats.put(friend, new Chat());

			//Notificamos la conexion al amigo
			friendClient.notifyConnectedFriend(client, user);

		}

		// Obtenemos la lista de peticiones de amistad de la base de datos
		ArrayList<FriendRequest> friendRequests = db.getFriendRequest(user.getId());

		User finalUser = new User(user.getId(), user.getName(), friendsClients, friendRequests, chats);

		// Guardamos el nuevo usuario conectado
		this.clients.put(user.hashCode(), client);

		return finalUser;

	}

	@Override
	public boolean validateRequest(ClientInterface client, String name, String password) throws RemoteException {

		User user = this.db.getUserWithPassword(name, password);

		if (user == null) {

			return false;

		}

		return client.equals(this.clients.get(user.hashCode()));

	}

	@Override
	public boolean register(String name, String password) throws RemoteException {

		return this.db.registerUser(name, password);

	}

	@Override
	public synchronized void logout(ClientInterface client, User user) throws RemoteException {

		ClientInterface value = this.clients.get(user.hashCode());

		if (client.equals(value)) {

			this.clients.remove(user.hashCode());

		}

		for (ClientInterface friendClient : user.getConnectedFriends().values()) {

			friendClient.notifyDisconnectedFriend(user);

		}

	}

	@Override
	public boolean requestFriend(ClientInterface client, String friendName, String name, String password) throws RemoteException {

		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}

		FriendRequest request = this.db.registerFriendRequest(name, friendName);

		if (request == null) {
			return false;
		}

		User friend = this.db.getUser(friendName);

		ClientInterface friendClient = this.clients.get(friend.hashCode());

		if (friendClient != null) {

			friendClient.notifyFriendRequest(request);

		}

		return true;

	}

	@Override
	public boolean acceptFriendRequest(ClientInterface client, int requestId, String friendName, String name, String password) throws RemoteException {

		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}

		boolean res = this.db.acceptFriendRequest(requestId, name, friendName);

		if (!res) {
			return false;
		}

		User me = this.db.getUser(name);
		User friend = this.db.getUser(friendName);

		ClientInterface friendClient = this.clients.get(friend.hashCode());

		if (friendClient == null) {
			return true;
		}

		client.notifyConnectedFriend(friendClient, friend);
		friendClient.notifyConnectedFriend(client, me);

		return true;

	}

	@Override
	public boolean declineFriendRequest(ClientInterface client, int requestId, String friendName, String name, String password) throws RemoteException {

		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}

		return this.db.declineFriendRequest(requestId, name, friendName);

	}

	@Override
	public boolean removeFriend(ClientInterface client, String friendName, String name, String password) throws RemoteException {

		boolean valid = this.validateRequest(client, name, password);

		if (!valid) {
			return false;
		}

		return this.db.removeFriend(name, friendName);

	}

}