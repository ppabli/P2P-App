package src.model;

public class ListUser {

	User user;
	int pendingChatMessages;

	public ListUser(User user, int pendingChatMessages) {

		this.user = user;
		this.pendingChatMessages = pendingChatMessages;

	}

	@Override
	public String toString() {

		return user.toString() + " - " + pendingChatMessages;

	}

	public User getUser() {

		return user;

	}

}