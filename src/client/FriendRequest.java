package src.client;

import javax.swing.*;
import java.awt.event.*;

public class FriendRequest extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField nameField;
	private JLabel errorLabel;
	private final ClientImpl client;

	public FriendRequest(ClientImpl client) {

		this.client = client;

		this.errorLabel.setOpaque(false);

		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				onCancel();

			}

		});

		this.pack();
		this.setVisible(true);

	}

	private void onOK() {

		String name = this.nameField.getText();

		if (name.isBlank()) {
			return;
		}

		PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

		if (!passwordConfirmation.getIsValid()) {
			return;
		}

		boolean res = this.client.requestFriend(name, passwordConfirmation.getName(), passwordConfirmation.getPassword());

		this.errorLabel.setOpaque(true);

		if (res) {

			this.errorLabel.setText("Friend request done");
			this.nameField.setText("");

		} else {

			this.errorLabel.setText("Friend request error");

		}

	}

	private void onCancel() {

		dispose();

	}

}