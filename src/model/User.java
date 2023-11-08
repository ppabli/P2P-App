package src.model;

import src.client.ClientInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class User implements Serializable {

	private final int id;
	private final String name;
	private final HashMap<User, ClientInterface> connectedFriends;
	private final ArrayList<FriendRequest> friendRequests;

	public User(int id, String name) {

		this.id = id;
		this.name = name;

		this.connectedFriends = new HashMap<>();
		this.friendRequests = new ArrayList<>();

	}

	public User(int id, String name, HashMap<User, ClientInterface> connectedFriends) {

		this.id = id;
		this.name = name;

		this.connectedFriends = connectedFriends;
		this.friendRequests = new ArrayList<>();

	}

	public User(int id, String name, HashMap<User, ClientInterface> connectedFriends, ArrayList<FriendRequest> friendRequests) {

		this.id = id;
		this.name = name;

		this.connectedFriends = connectedFriends;
		this.friendRequests = friendRequests;

	}

	public int getId() {

		return id;

	}

	public String getName() {

		return name;

	}

	public HashMap<User, ClientInterface>  getConnectedFriends() {

		return connectedFriends;

	}

	public ArrayList<FriendRequest> getFriendRequests() {

		return friendRequests;

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

		return id == user.id;

	}

	@Override
	public int hashCode() {

		return Objects.hash(id);

	}

	public void addFriendRequest(FriendRequest request) {

		this.friendRequests.add(request);

	}

	public void addConnectedFriend(User user, ClientInterface client) {

		this.connectedFriends.put(user, client);

	}

	public void removeConnectedFriend(User user) {

		this.connectedFriends.remove(user);

	}

	public void removeFriendRequest(int requestId) {

		FriendRequest friendRequest = null;

		for (FriendRequest request : this.friendRequests) {

			if (request.getId() == requestId) {

				friendRequest = request;

			}

		}

		if (friendRequest != null) {

			this.friendRequests.remove(friendRequest);

		}

	}

}