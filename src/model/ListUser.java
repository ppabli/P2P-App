package src.model;

import java.io.Serializable;

public class ListUser implements Serializable {

	User user;
	int pendingChatMessages;

	public ListUser(User user, int pendingChatMessages) {

		this.user = user;
		this.pendingChatMessages = pendingChatMessages;

	}

	@Override
	public String toString() {

		return this.user.toString() + " - " + this.pendingChatMessages;

	}

	public User getUser() {

		return this.user;

	}

}