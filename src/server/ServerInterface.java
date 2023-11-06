package src.server;

import src.client.ClientInterface;
import src.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

	User login(ClientInterface client, String name, String password) throws RemoteException;

	boolean validateRequest(ClientInterface client, String name, String password) throws RemoteException;

	boolean register(String name, String password) throws RemoteException;

	void logout(ClientInterface client, int userId) throws RemoteException;

	boolean requestFriend(ClientInterface client, String friendName, String userName, String password) throws RemoteException;

	void approveFriendRequest(ClientInterface client, int requestId, String friendName, String userName, String password) throws RemoteException;

}