package src.client;

import src.model.Chat;
import src.model.ListUser;
import src.model.User;
import src.observer.Observer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App implements Observer {

	private JPanel mainPanel;
	private JList<ListUser> friendList;
	private JButton frButton;
	private JLabel tittleLabel;
	private JButton addFriendButton;
	private JLabel cfLabel;
	private JTextField messageField;
	private JButton sendButton;
	private JPanel chatPanel;
	private JLabel nameLabel;
	private JTable chatTable;
	private JButton removeFriendButton;
	private final ClientImpl client;
	private User activeChatUser;

	public App(ClientImpl client) {

		this.client = client;
		this.client.addObserver(this);

		this.chatPanel.setVisible(false);
		this.tittleLabel.setText("Welcome " + client.getUser().getName());

		JFrame frame = new JFrame("CoDis - Mega ultra pack - Exam edition");
		frame.setContentPane(this.mainPanel);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				client.requestLogout();
				client.end();

				frame.dispose();

			}

		});

		addFriendButton.addActionListener(e -> new FriendRequest(client));

		frButton.addActionListener(e -> new FriendRequests(client));

		friendList.addListSelectionListener(e -> {

			ListUser listUser = friendList.getSelectedValue();

			if (listUser == null) {
				return;
			}

			this.activeChatUser = listUser.getUser();

			this.chatPanel.setVisible(true);
			this.nameLabel.setText(listUser.getUser().getName());

			updateChat();

		});

		sendButton.addActionListener(e -> {

			String message = messageField.getText();

			if (message.isBlank()) {
				return;
			}

			this.client.sendMessage(activeChatUser, message);
			this.updateChat();

		});

		removeFriendButton.addActionListener(e -> {

			PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

			if (!passwordConfirmation.getIsValid()) {
				return;
			}

			boolean res = client.requestRemoveFriend(activeChatUser, passwordConfirmation.getName(), passwordConfirmation.getPassword());

			if (!res) {
				return;
			}

			this.activeChatUser = null;
			this.chatPanel.setVisible(false);
			this.friendList.clearSelection();

		});

		this.updateConnectedFriends();
		this.updateFriendRequests();

		frame.pack();
		frame.setVisible(true);

	}

	public void updateFriendRequests() {

		this.frButton.setText("Friend requests - " + client.getUser().getFriendRequests().size());

	}

	public void updateConnectedFriends() {

		this.cfLabel.setText("Connected friends  " + client.getUser().getConnectedFriends().size());

		boolean activeUserLogout = true;

		DefaultListModel<ListUser> listModel = new DefaultListModel<>();

		for (User user : client.getUser().getConnectedFriends().keySet()) {

			if (user.equals(activeChatUser)) {
				activeUserLogout = false;
			}

			Chat chat = this.client.getUser().getChats().get(user);

			listModel.addElement(new ListUser(user, chat.getPendingMessagesCount()));

		}

		if (activeUserLogout) {

			this.activeChatUser = null;
			this.chatPanel.setVisible(false);
			this.friendList.clearSelection();

		}

		this.friendList.setModel(listModel);

	}

	public void updateChat() {

		Chat chat = this.client.getUser().getChats().get(this.activeChatUser);

		chat.readMessages();
		updateConnectedFriends();

		DefaultTableModel model = new DefaultTableModel();

		model.setColumnCount(2);

		for (int i = 0; i < chat.getMessages().size(); i++) {

			boolean myMessage = chat.getOrder().get(i);

			Object[] rowData;

			if (myMessage) {

				rowData = new Object[]{"", chat.getMessages().get(i)};

			} else {

				rowData = new Object[]{chat.getMessages().get(i), ""};

			}

			model.addRow(rowData);

		}

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.RIGHT);

		this.chatTable.setModel(model);
		this.chatTable.getColumnModel().getColumn(1).setCellRenderer(renderer);

	}

	@Override
	public void updateChats(User user) {

		if (user.equals(activeChatUser)) {

			updateChat();

		} else {

			updateConnectedFriends();

		}

	}

}