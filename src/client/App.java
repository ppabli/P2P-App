package src.client;

import src.observer.Observer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;

public class App implements Observer {

	private JPanel mainPanel;
	private JList list1;
	private JButton frButton;
	private JLabel tittleLabel;
	private JButton addFriendButton;
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

				try {

					UnicastRemoteObject.unexportObject(client, true);

				} catch (NoSuchObjectException ex) {

					throw new RuntimeException(ex);

				}

			}

		});

		this.frButton.setText("Friend requests - " + client.getUser().getFriendRequests().size());
		this.tittleLabel.setText("Welcome " + client.getUser().getName());

		frame.pack();
		frame.setVisible(true);

		addFriendButton.addActionListener(e -> new FriendRequest(client));

	}

	public void updateFriendRequests() {

		this.frButton.setText("Friend requests - " + client.getUser().getFriendRequests().size());

	}

}