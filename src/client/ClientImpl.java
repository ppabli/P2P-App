package src.client;

import src.model.FriendRequest;
import src.model.User;
import src.observer.Observer;
import src.server.ServerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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

	public void addObserver(Observer observer) {

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

			this.serverObject.logout(this, this.user.getId());

		} catch (Exception e) {

			System.out.println("Client | Error: " + e.getMessage());

		}

	}

	@Override
	public void notifyMessage() throws RemoteException {

	}

	@Override
	public void notifyFriendRequest(FriendRequest request) throws RemoteException {

		this.user.addFriendRequest(request);

		for(Observer observer : this.observers) {

			observer.updateFriendRequests();

		}

	}

	@Override
	public void notifyConnectedFriend(ClientInterface client, User user) throws RemoteException {

	}

	public User getUser() {

		return this.user;

	}

}