package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class PasswordConfirmation extends javax.swing.JDialog {

    private final ClientImpl client;
    public boolean isValid;

    public PasswordConfirmation(ClientImpl client) {

        initComponents();

        this.isValid = false;
        this.client = client;

        this.errorLabel.setOpaque(false);
        this.getRootPane().setDefaultButton(buttonOK);

        this.setModal(true);
        // Si se cierra abruptamente deslogueamos al cliente
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        this.errorLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.errorLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));

        this.pack();
        this.setVisible(true);
        this.toFront();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonOK = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        buttonCancel = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        passwordField.setBackground(new java.awt.Color(220, 232, 255));
        passwordField.setBorder(null);
        getContentPane().add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 330, 50));

        buttonCancel.setBorder(null);
        buttonCancel.setBorderPainted(false);
        buttonCancel.setContentAreaFilled(false);
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        getContentPane().add(buttonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 620, 110, 30));
        getContentPane().add(errorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, -1, -1));

        nameField.setBackground(new java.awt.Color(220, 232, 255));
        nameField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        nameField.setBorder(null);
        getContentPane().add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 206, 310, 60));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/ConfirmIdentity.png"))); // NOI18N
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1270, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed

        String name = this.nameField.getText();
        String password = new String(this.passwordField.getPassword());

        boolean isValid = this.client.requestValidation(name, password);

        this.errorLabel.setOpaque(true);

        if (isValid) {

            this.isValid = true;
            this.dispose();

        } else {

            this.errorLabel.setOpaque(true);
            this.errorLabel.setText("User/password invalid");

        }
    }//GEN-LAST:event_buttonOKActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed

        this.dispose();

    }//GEN-LAST:event_buttonCancelActionPerformed

    public String getName() {
        return this.nameField.getText();
    }

    public String getPassword() {
        return new String(this.passwordField.getPassword());
    }

    public boolean getIsValid() {
        return this.isValid;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOK;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JPasswordField passwordField;
    // End of variables declaration//GEN-END:variables
}
