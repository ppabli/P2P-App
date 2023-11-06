package src.client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;

public class Login extends JDialog {
	private JPanel contentPane;
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

		registerButton.addActionListener(e -> {

			System.out.println("pass");

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

		boolean res = this.client.requestLogin(userName, password);

		if (res) {

			new App(this.client);
			this.onCancel();

		} else {

			this.errorLabel.setOpaque(true);
			this.errorLabel.setText("User/password not valid");

		}

	}

	private void onCancel() {

		dispose();

		try {

			UnicastRemoteObject.unexportObject(this.client, true);

		} catch (NoSuchObjectException e) {

			throw new RuntimeException(e);

		}

	}

}