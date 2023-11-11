package server;

import client.ClientInterface;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

	User login(ClientInterface client, String name, String password) throws RemoteException;

	boolean validateRequest(ClientInterface client, String name, String password) throws RemoteException;

	boolean register(String name, String password) throws RemoteException;

	void logout(ClientInterface client, User user) throws RemoteException;

	boolean requestFriend(ClientInterface client, String friendName, String userName, String password) throws RemoteException;

	boolean acceptFriendRequest(ClientInterface client, int requestId, String friendName, String userName, String password) throws RemoteException;

	boolean declineFriendRequest(ClientInterface client, int requestId, String friendName, String userName, String password) throws RemoteException;

	boolean removeFriend(ClientInterface client, String friendName, String name, String password) throws RemoteException;

}