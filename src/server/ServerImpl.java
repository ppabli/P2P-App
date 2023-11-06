package src.server;

import src.client.Client;
import src.client.ClientInterface;
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
	public User login(ClientInterface client, String name, String password) throws RemoteException {

		// Obtenemos los datos basicos del usuario
		User user = db.getUserWithPassword(name, password);

		if (user == null) {
			return null;
		}

		// Obtenemos la lista de amigos de la base de datos
		ArrayList<User> userFriends = db.getFriends(user.getId());

		// Creamos un array con los clientes que tenemos conectamos que sean amigos
		ArrayList<ClientInterface> friendsClients = new ArrayList<>();

		// Iteramos por la lista de amigos buscando los que esten conectados
		for (User friend : userFriends) {

			// Buscamos en los clientes conectados segun el nombre del amigo
			friendsClients.add(this.clients.get(friend.hashCode()));

		}

		// Obtenemos la lista de peticiones de amistad de la base de datos
		ArrayList<FriendRequest> friendRequests = db.getFriendRequest(user.getId());

		User finalUser = new User(user.getId(), user.getName(), friendsClients, friendRequests);

		// Guardamos el nuevo usuario conectado
		this.clients.put(user.getId(), client);

		return finalUser;

	}

	@Override
	public boolean validateRequest(ClientInterface client, String name, String password) throws RemoteException {

		User user = this.db.getUserWithPassword(name, password);

		if (user == null) {

			return false;

		}

		return client.equals(this.clients.get(user.getId()));

	}

	@Override
	public boolean register(String name, String password) throws RemoteException {

		return this.db.registerUser(name, password);

	}

	@Override
	public void logout(ClientInterface client, int userId) throws RemoteException {

		ClientInterface value = this.clients.get(userId);

		if (value == client) {

			this.clients.remove(userId);

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

		ClientInterface friendClient = this.clients.get(friend.getId());

		if (friendClient != null) {

			friendClient.notifyFriendRequest(request);

		}

		return true;

	}

	@Override
	public void approveFriendRequest(ClientInterface client, int requestId, String friendName, String userName, String password) {

	}

}