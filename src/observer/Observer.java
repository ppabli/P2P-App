package src.observer;

import src.model.User;

public interface Observer {

	void updateFriendRequests();
	void updateConnectedFriends();
	void updateChats(User user);

}