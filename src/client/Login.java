package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JDialog {
	private JPanel mainPanel;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JPasswordField passwordField;
	private JTextField nameText;
	private JLabel errorLabel;
	private JButton registerButton;
	private final ClientImpl client;

	public Login(ClientImpl client) {

		this.client = client;

		this.errorLabel.setOpaque(false);

		setContentPane(mainPanel);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> {

			this.client.end();

			onCancel();

		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				client.end();

				onCancel();

			}

		});

		registerButton.addActionListener(e -> {

			boolean res = client.requestRegister(nameText.getText(), new String(this.passwordField.getPassword()));

			errorLabel.setOpaque(true);

			if (res) {

				errorLabel.setText("User created successfully");

			} else {

				errorLabel.setText("Error creating user");

			}

		});

		this.pack();
		this.setVisible(true);
		this.toFront();

	}

	private void onOK() {

		String userName = this.nameText.getText();
		String password = new String(this.passwordField.getPassword());

		if (userName.isBlank() || password.isBlank()) {
			return;
		}

		boolean res = this.client.requestLogin(userName, password);

		if (res) {

			new App(this.client);
			this.onCancel();

		} else {

			this.errorLabel.setOpaque(true);
			this.errorLabel.setText("User or password not valid");

		}

	}

	private void onCancel() {

		dispose();

	}

}