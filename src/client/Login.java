package client;

public class Login extends javax.swing.JFrame {

    private final ClientImpl client;

    public Login(ClientImpl client) {

        initComponents();

        this.client = client;
        this.errorLabel.setOpaque(false);
        this.getRootPane().setDefaultButton(buttonOK);
        this.pack();
        this.setVisible(true);
        this.toFront();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonOK = new javax.swing.JButton();
        nameText = new javax.swing.JTextField();
        errorLabel = new javax.swing.JLabel();
        buttonCancel = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        registerButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonOK.setBorder(null);
        buttonOK.setBorderPainted(false);
        buttonOK.setContentAreaFilled(false);
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });
        getContentPane().add(buttonOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 390, 330, 50));

        nameText.setBackground(new java.awt.Color(220, 232, 255));
        nameText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        nameText.setBorder(null);
        getContentPane().add(nameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 206, 310, 60));
        getContentPane().add(errorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, -1, -1));

        buttonCancel.setBorder(null);
        buttonCancel.setBorderPainted(false);
        buttonCancel.setContentAreaFilled(false);
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        getContentPane().add(buttonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 620, 110, 30));

        passwordField.setBackground(new java.awt.Color(220, 232, 255));
        passwordField.setBorder(null);
        getContentPane().add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 330, 50));

        registerButton.setBorder(null);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        getContentPane().add(registerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, 160, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Login.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1270, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed

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

    }//GEN-LAST:event_buttonOKActionPerformed

    private void onCancel() {

        dispose();

    }

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed

        boolean res = client.requestRegister(nameText.getText(), new String(this.passwordField.getPassword()));

        errorLabel.setOpaque(true);

        if (res) {

            errorLabel.setText("User created successfully");

        } else {

            errorLabel.setText("Error creating user");

        }

    }//GEN-LAST:event_registerButtonActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed

        this.client.end();

        dispose();

    }//GEN-LAST:event_buttonCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOK;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nameText;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
