package src.server;

import src.model.FriendRequest;
import src.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

	private final Connection con;

	public DataBase() throws SQLException {

		this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");

	}

	public boolean registerUser(String name, String password) {

		try {

			PreparedStatement statement = con.prepareStatement("insert into users (name, password) values (?, ?)");

			statement.setString(1, name);
			statement.setString(2, password);

			statement.executeUpdate();

			return true;

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return false;

		}

	}

	public FriendRequest registerFriendRequest(String name, String friendName) {

		try {

			// Obtenemos los usuarios
			User user1 = this.getUser(name);
			User user2 = this.getUser(friendName);

			// Comprobamos que no este ya en sus amigos
			ArrayList<User> friends = this.getFriends(user1.getId());

			if (friends.contains(user2)) {
				return null;
			}

			ArrayList<FriendRequest> requests = this.getFriendRequest(user2.getId());

			if (requests.contains(new FriendRequest(-1, user1.getName()))) {
				return null;
			}

			PreparedStatement statement = this.con.prepareStatement("insert into friend_requests (user_id, friend_id) values (?, ?)");

			statement.setInt(1, user1.getId());
			statement.setInt(2, user2.getId());

			int res = statement.executeUpdate();

			if (res > 0) {

				ArrayList<FriendRequest> request = this.getFriendRequest(user2.getId());
				return request.get(request.size() - 1);

			}

			return null;

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return null;

		}

	}

	public boolean acceptFriendRequest(int requestId, String name, String friendName) {

		try {

			User user1 = this.getUser(name);
			User user2 = this.getUser(friendName);

			PreparedStatement statement = this.con.prepareStatement("select * from friend_requests where id = ? and user_id = ? and friend_id = ?");

			statement.setInt(1, requestId);
			statement.setInt(2, user2.getId());
			statement.setInt(3, user1.getId());

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {

				// Borramos la peticion
				statement = this.con.prepareStatement("delete from friend_requests where id = ? and user_id = ? and friend_id = ?");

				statement.setInt(1, requestId);
				statement.setInt(2, user2.getId());
				statement.setInt(3, user1.getId());

				int res = statement.executeUpdate();

				if (res > 0) {

					statement = this.con.prepareStatement("insert into friends (user_id, friend_id) values (?, ?), (?, ?)");

					statement.setInt(1, user1.getId());
					statement.setInt(2, user2.getId());
					statement.setInt(3, user2.getId());
					statement.setInt(4, user1.getId());

					res = statement.executeUpdate();

					return res > 0;

				} else {

					return false;

				}

			} else {

				return false;

			}

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return false;

		}

	}

	public boolean declineFriendRequest(int requestId, String name, String friendName) {

		try {

			User user1 = this.getUser(name);
			User user2 = this.getUser(friendName);

			PreparedStatement statement = this.con.prepareStatement("select * from friend_requests where id = ? and user_id = ? and friend_id = ?");

			statement.setInt(1, requestId);
			statement.setInt(2, user2.getId());
			statement.setInt(3, user1.getId());

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {

				// Borramos la peticion
				statement = this.con.prepareStatement("delete from friend_requests where id = ? and user_id = ? and friend_id = ?");

				statement.setInt(1, requestId);
				statement.setInt(2, user2.getId());
				statement.setInt(3, user1.getId());

				int res = statement.executeUpdate();

				return res > 0;

			} else {

				return false;

			}

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return false;

		}

	}

	public User getUser(String name) {

		try {

			// Obtenemos el usuario de la base de datos
			// Preparamos la query
			PreparedStatement statement = con.prepareStatement("select * from users where name = ?");
			statement.setString(1, name);

			// Ejecutamos la consulta
			ResultSet rs = statement.executeQuery();

			// Si existe alguno
			if (rs.next()) {

				return new User(rs.getInt("id"), rs.getString("name"));

			} else {

				return null;

			}

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return null;

		}

	}

	public User getUserWithPassword(String name, String password) {

		try {

			// Obtenemos el usuario de la base de datos
			// Preparamos la query
			PreparedStatement statement = con.prepareStatement("select * from users where name = ? and password = ?");
			statement.setString(1, name);
			statement.setString(2, password);

			// Ejecutamos la consulta
			ResultSet rs = statement.executeQuery();

			// Si existe alguno
			if (rs.next()) {

				return new User(rs.getInt("id"), rs.getString("name"));

			} else {

				return null;

			}

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return null;

		}

	}

	public ArrayList<User> getFriends(int userId) {

		try {

			PreparedStatement statement = con.prepareStatement("select * from friends where user_id = ?");
			statement.setInt(1, userId);

			ResultSet rs = statement.executeQuery();

			ArrayList<User> friends = new ArrayList<>();

			while (rs.next()) {

				int friendId = rs.getInt("friend_id");

				PreparedStatement statement2 = con.prepareStatement("select * from users where id = ?");
				statement2.setInt(1, friendId);

				ResultSet rs2 = statement2.executeQuery();

				if (rs2.next()) {

					int id = rs2.getInt("id");
					String name = rs2.getString("name");

					friends.add(new User(id, name));

				}

			}

			return friends;

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return null;

		}

	}

	public ArrayList<FriendRequest> getFriendRequest(int userId) {

		try {

			PreparedStatement statement = con.prepareStatement("select * from friend_requests where friend_id = ?");
			statement.setInt(1, userId);

			ResultSet rs = statement.executeQuery();

			ArrayList<FriendRequest> requests = new ArrayList<>();

			while (rs.next()) {

				int requestId = rs.getInt("id");
				int friendId = rs.getInt("user_id");

				PreparedStatement statement2 = con.prepareStatement("select * from users where id = ?");
				statement2.setInt(1, friendId);

				ResultSet rs2 = statement2.executeQuery();

				if (rs2.next()) {

					String friendName = rs2.getString("name");

					requests.add(new FriendRequest(requestId, friendName));

				}

			}

			return requests;

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return null;

		}

	}

	public boolean removeFriend(String name, String friendName) {

		try {

			User me = this.getUser(name);
			User friend = this.getUser(friendName);

			ArrayList<User> friends = this.getFriends(me.getId());

			if (!friends.contains(friend)) {
				return true;
			}

			PreparedStatement statement = this.con.prepareStatement("delete from friends where (user_id = ? and friend_id = ?) or (user_id = ? and friend_id = ?)");

			statement.setInt(1, me.getId());
			statement.setInt(2, friend.getId());
			statement.setInt(3, friend.getId());
			statement.setInt(4, me.getId());

			int res = statement.executeUpdate();

			return res > 0;

		} catch (Exception e) {

			System.out.println("Database | Error: " + e.getMessage());
			return false;

		}

	}

}