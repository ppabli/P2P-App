package src.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {

	ArrayList<String> messages;
	ArrayList<Boolean> order;
	int pendingMessagesCount;

	public Chat() {

		messages = new ArrayList<>();
		order = new ArrayList<>();

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

		return messages;

	}

	public ArrayList<Boolean> getOrder() {

		return order;

	}

	public int getPendingMessagesCount() {

		return pendingMessagesCount;

	}

}