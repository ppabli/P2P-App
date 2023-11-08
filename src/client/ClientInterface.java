package src.client;

import src.model.FriendRequest;
import src.model.User;

import java.rmi.*;

public interface ClientInterface extends Remote {

	void notifyMessage() throws RemoteException;

	void notifyFriendRequest(FriendRequest request) throws RemoteException;

	void notifyConnectedFriend(ClientInterface client, User user) throws RemoteException;

	void notifyDisconnectedFriend(User user) throws RemoteException;

}