package src.client;

import src.model.Chat;
import src.model.FriendRequest;
import src.model.User;
import src.observer.Observer;
import src.server.ServerInterface;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private final ServerInterface serverObject;
	private User user;
	private final ArrayList<Observer> observers;

	protected ClientImpl(ServerInterface serverObject) throws Exception {

		super();

		this.serverObject = serverObject;

		this.observers = new ArrayList<>();

		new Login(this);

	}

	public synchronized void addObserver(Observer observer) {

		this.observers.add(observer);

	}

	public boolean requestFriend(String friendName, String name, String password) {

		try {

			return this.serverObject.requestFriend(this, friendName, name, password);

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;

		}

	}

	public boolean requestLogin(String name, String password) {

		try {

			this.user = this.serverObject.login(this, name, password);

			return this.user != null;

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;

		}

	}

	public boolean requestValidation(String name, String password) {

		try {

			return this.serverObject.validateRequest(this, name, password);

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;

		}

	}

	public boolean requestRegister(String name, String password) {

		try {

			return this.serverObject.register(name, password);

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;

		}

	}

	public void requestLogout() {

		try {

			this.serverObject.logout(this, this.user);

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());

		}

	}

	public synchronized boolean requestAcceptFriendRequest(int requestId, String friendName, String name, String password) {

		try {

			boolean res = this.serverObject.acceptFriendRequest(this, requestId, friendName, name, password);

			if (!res) {
				return false;
			}

			this.user.removeFriendRequest(friendName);

			for(Observer observer : this.observers) {

				observer.updateFriendRequests();

			}

			return true;

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;
		}

	}

	public synchronized boolean requestDeclineFriendRequest(int requestId, String friendName, String name, String password) {

		try {

			boolean res = this.serverObject.declineFriendRequest(this, requestId, friendName, name, password);

			if (!res) {
				return false;
			}

			this.user.removeFriendRequest(friendName);

			for(Observer observer : this.observers) {

				observer.updateFriendRequests();

			}

			return true;

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;

		}

	}

	public synchronized boolean requestRemoveFriend(User friend, String name, String password) {

		try {

			boolean res = this.serverObject.removeFriend(this, friend.getName(), name, password);

			if (!res) {
				return false;
			}

			this.user.removeConnectedFriend(friend, true);

			for(Observer observer : this.observers) {

				observer.updateConnectedFriends();

			}

			return true;

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());
			return false;

		}

	}

	@Override
	public synchronized void notifyMessage(ClientInterface friendClient, User user, String message) throws RemoteException {

		User friend = null;

		for (Map.Entry<User, ClientInterface> entry : this.user.getConnectedFriends().entrySet()) {

			if (entry.getValue().equals(friendClient)) {

				friend = entry.getKey();
				break;

			}

		}

		if (friend == null || !friend.equals(user)) {
			return;
		}

		Chat chat = this.user.getChats().get(friend);

		chat.addMessage(message, false);

		for(Observer observer : this.observers) {

			observer.updateChats(friend);

		}

	}

	@Override
	public synchronized void notifyFriendRequest(FriendRequest request) throws RemoteException {

		this.user.addFriendRequest(request);

		for(Observer observer : this.observers) {

			observer.updateFriendRequests();

		}

	}

	@Override
	public synchronized void notifyConnectedFriend(ClientInterface client, User user) throws RemoteException {

		this.user.addConnectedFriend(user, client);

		for(Observer observer : this.observers) {

			observer.updateConnectedFriends();

		}

	}

	@Override
	public synchronized void notifyDisconnectedFriend(User user) throws RemoteException {

		this.user.removeConnectedFriend(user, false);

		for(Observer observer : this.observers) {

			observer.updateConnectedFriends();

		}

	}

	public void sendMessage(User user, String message) {

		try {

			ClientInterface friendClient = this.user.getConnectedFriends().get(user);

			if (friendClient == null) {
				return;
			}

			friendClient.notifyMessage(this, new User(this.user.getId(), this.user.getName()), message);
			Chat chat = this.user.getChats().get(user);

			chat.addMessage(message, true);

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());

		}

	}

	public User getUser() {

		return this.user;

	}

	public void end() {

		try {

			UnicastRemoteObject.unexportObject(this, true);

		} catch (NoSuchObjectException e) {

			System.out.println("Client | Error: " + e.getMessage());

		}

	}

}