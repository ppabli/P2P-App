package src.client;

import src.model.User;
import src.observer.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;

public class App implements Observer {

	private JPanel mainPanel;
	private JList<User> friendList;
	private JButton frButton;
	private JLabel tittleLabel;
	private JButton addFriendButton;
	private JLabel cfLabel;
	private final ClientImpl client;

	public App(ClientImpl client) {

		this.client = client;
		this.client.addObserver(this);

		JFrame frame = new JFrame("Main");
		frame.setContentPane(this.mainPanel);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				client.requestLogout();
				frame.dispose();

			}

		});

		this.tittleLabel.setText("Welcome " + client.getUser().getName());

		this.updateConnectedFriends();
		this.updateFriendRequests();

		frame.pack();
		frame.setVisible(true);

		addFriendButton.addActionListener(e -> new FriendRequest(client));

		frButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new FriendRequests(client);

			}

		});

	}

	public void updateFriendRequests() {

		this.frButton.setText("Friend requests - " + client.getUser().getFriendRequests().size());

	}

	public void updateConnectedFriends() {

		this.cfLabel.setText("Connected friends  " + client.getUser().getConnectedFriends().size());

		DefaultListModel<User> listModel = new DefaultListModel<>();
		for (User user : client.getUser().getConnectedFriends().keySet()) {

			listModel.addElement(user);

		}

		this.friendList.setModel(listModel);

	}

}