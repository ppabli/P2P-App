package src.client;

import src.model.FriendRequest;
import src.model.User;
import src.observer.Observer;

import javax.swing.*;

public class FriendRequests implements Observer {

	private JPanel mainPanel;
	private JButton acceptButton;
	private JButton declineButton;
	private JLabel errorLabel;
	private JList<FriendRequest> friendRequestList;
	private final ClientImpl client;

	public FriendRequests(ClientImpl client) {

		this.client = client;

		errorLabel.setOpaque(false);

		acceptButton.setEnabled(false);
		declineButton.setEnabled(false);

		JFrame frame = new JFrame("Friend Requests view");
		frame.setContentPane(this.mainPanel);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.updateFriendRequests();

		frame.pack();
		frame.setVisible(true);

		acceptButton.addActionListener(e -> this.requestAction(0));
		declineButton.addActionListener(e -> this.requestAction(1));

	}

	private void requestAction(int action) {

		FriendRequest request = this.friendRequestList.getSelectedValue();

		if (request == null) {
			return;
		}

		PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

		if (!passwordConfirmation.getIsValid()) {
			return;
		}

		boolean res;

		if (action == 0) {

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