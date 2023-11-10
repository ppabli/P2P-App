package src.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {

	ArrayList<String> messages;
	ArrayList<Boolean> order;
	int pendingMessagesCount;

	public Chat() {

		this.messages = new ArrayList<>();

		// Variable que mide si el mensaje es tuyo o de tu amigo
		// true: es tuyo
		// false: es de tu amigo
		this.order = new ArrayList<>();

	}

	public void addMessage(String message, boolean isMyMessage) {

		this.messages.add(message);
		this.order.add(isMyMessage);
		this.pendingMessagesCount++;

	}

	public void readMessages() {

		this.pendingMessagesCount = 0;

	}

	public ArrayList<String> getMessages() {

		return this.messages;

	}

	public ArrayList<Boolean> getOrder() {

		return this.order;

	}

	public int getPendingMessagesCount() {

		return this.pendingMessagesCount;

	}

}