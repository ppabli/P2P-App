package src.client;

import src.model.FriendRequest;
import src.model.User;
import src.observer.Observer;

import javax.swing.*;

enum Action {

	ACCEPT, DECLINE

}

public class FriendRequests implements Observer {

	private JPanel mainPanel;
	private JButton acceptButton;
	private JButton declineButton;
	private JLabel errorLabel;
	private JList<FriendRequest> friendRequestList;
	private final ClientImpl client;

	public FriendRequests(ClientImpl client) {

		this.client = client;
		this.client.addObserver(this);

		errorLabel.setOpaque(false);
		acceptButton.setEnabled(false);
		declineButton.setEnabled(false);

		JFrame frame = new JFrame("Friend Requests view");
		frame.setContentPane(this.mainPanel);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.updateFriendRequests();

		acceptButton.addActionListener(e -> this.requestAction(Action.ACCEPT));

		declineButton.addActionListener(e -> this.requestAction(Action.DECLINE));

		friendRequestList.addListSelectionListener(e -> {

			boolean validSelection = friendRequestList.getSelectedIndex() >= 0;

			acceptButton.setEnabled(validSelection);
			declineButton.setEnabled(validSelection);

		});

		frame.pack();
		frame.setVisible(true);
		frame.toFront();

	}

	private void requestAction(Action action) {

		FriendRequest request = this.friendRequestList.getSelectedValue();

		if (request == null) {
			return;
		}

		PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

		if (!passwordConfirmation.getIsValid()) {
			return;
		}

		boolean res;

		if (action == Action.ACCEPT) {

			res = client.requestAcceptFriendRequest(request.getId(), request.getName(), passwordConfirmation.getName(), passwordConfirmation.getPassword());

		} else {

			res = client.requestDeclineFriendRequest(request.getId(), request.getName(), passwordConfirmation.getName(), passwordConfirmation.getPassword());

		}

		if (res) {

			errorLabel.setText("Done!");

		} else {

			errorLabel.setOpaque(false);
			errorLabel.setText("Error with friend request");

		}

	}

	@Override
	public void updateFriendRequests() {

		// Creamos los elementos de la lista
		DefaultListModel<FriendRequest> listModel = new DefaultListModel<>();

		// Añadimos los elementos a la lista
		for (FriendRequest request : client.getUser().getFriendRequests()) {

			listModel.addElement(request);

		}

		this.friendRequestList.setModel(listModel);
		this.friendRequestList.clearSelection();

	}

	@Override
	public void updateConnectedFriends() {
		// Nothing to do
	}

	@Override
	public void updateChats(User user) {
		// Nothing to do
	}

}