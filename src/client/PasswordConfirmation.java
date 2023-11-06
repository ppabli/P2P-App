package src.client;

import javax.swing.*;
import java.awt.event.*;

public class PasswordConfirmation extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JLabel errorLabel;
	private final ClientImpl client;

	private boolean isValid;
	public PasswordConfirmation(ClientImpl client) {

		this.isValid = false;
		this.client = client;

		this.errorLabel.setOpaque(false);

		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		this.pack();
		this.setVisible(true);
		this.toFront();

	}

	private void onOK() {

		String name = this.nameField.getText();
		String password = new String(this.passwordField.getPassword());

		boolean isValid = this.client.requestValidation(name, password);

		this.errorLabel.setOpaque(true);

		if (isValid) {

			this.dispose();
			this.isValid = true;

		} else {

			this.errorLabel.setOpaque(true);
			this.errorLabel.setText("User/password invalid");

		}

	}

	private void onCancel() {

		dispose();

	}

	public String getName() {

		return this.nameField.getText();

	}

	public String getPassword() {

		return new String(this.passwordField.getPassword());

	}

	public boolean getIsValid() {

		return this.isValid;

	}

}