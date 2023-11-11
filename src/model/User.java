package model;

import client.ClientInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class User implements Serializable {

	private final int id;
	private final String name;
	private final HashMap<User, ClientInterface> connectedFriends;
	private final HashMap<User, Chat> chats;
	private final ArrayList<FriendRequest> friendRequests;

	public User(int id, String name) {

		this.id = id;
		this.name = name;

		this.connectedFriends = new HashMap<>();
		this.friendRequests = new ArrayList<>();
		this.chats = new HashMap<>();

	}

	public User(int id, String name, HashMap<User, ClientInterface> connectedFriends) {

		this.id = id;
		this.name = name;

		this.connectedFriends = connectedFriends;
		this.friendRequests = new ArrayList<>();
		this.chats = new HashMap<>();

	}

	public User(int id, String name, HashMap<User, ClientInterface> connectedFriends, ArrayList<FriendRequest> friendRequests) {

		this.id = id;
		this.name = name;

		this.connectedFriends = connectedFriends;
		this.friendRequests = friendRequests;
		this.chats = new HashMap<>();

	}

	public User(int id, String name, HashMap<User, ClientInterface> connectedFriends, ArrayList<FriendRequest> friendRequests, HashMap<User, Chat> chats) {

		this.id = id;
		this.name = name;

		this.connectedFriends = connectedFriends;
		this.friendRequests = friendRequests;
		this.chats = chats;

	}

	public HashMap<User, Chat> getChats() {

		return this.chats;

	}

	public int getId() {

		return this.id;

	}

	public String getName() {

		return this.name;

	}

	public HashMap<User, ClientInterface>  getConnectedFriends() {

		return this.connectedFriends;

	}

	public ArrayList<FriendRequest> getFriendRequests() {

		return this.friendRequests;

	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {

			return true;

		}

		if (o == null || getClass() != o.getClass()) {

			return false;

		}

		User user = (User) o;

		return this.id == user.id;

	}

	@Override
	public int hashCode() {

		return Objects.hash(this.id);

	}

	@Override
	public String toString() {

		return this.name;

	}

	public void addFriendRequest(FriendRequest request) {

		this.friendRequests.add(request);

	}

	public void addConnectedFriend(User user, ClientInterface client) {

		this.connectedFriends.put(user, client);

		if (this.chats.get(user) == null) {

			this.chats.put(user, new Chat());

		}

	}

	public void removeConnectedFriend(User user, boolean removeChat) {

		this.connectedFriends.remove(user);

		if (removeChat) {

			this.chats.remove(user);

		}

	}

	public void removeFriendRequest(String userName) {

		int index = this.friendRequests.indexOf(new FriendRequest(-1, userName));

		if (index < 0) {
			return;
		}

		this.friendRequests.remove(index);

	}

}