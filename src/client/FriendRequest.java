package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class FriendRequest extends javax.swing.JFrame {

    private final ClientImpl client;

    public FriendRequest(ClientImpl client) {

        initComponents();

        this.client = client;

        this.errorLabel.setVisible(false);

        this.getRootPane().setDefaultButton(buttonOK);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        nameField = new javax.swing.JTextField();
        errorLabel = new javax.swing.JLabel();
        buttonCancel = new javax.swing.JButton();
        buttonOK = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nameField.setBackground(new java.awt.Color(220, 232, 255));
        nameField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        nameField.setBorder(null);
        getContentPane().add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, 230, 50));
        getContentPane().add(errorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, -1, -1));

        buttonCancel.setBorder(null);
        buttonCancel.setBorderPainted(false);
        buttonCancel.setContentAreaFilled(false);
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        getContentPane().add(buttonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 250, 130, 30));

        buttonOK.setBorder(null);
        buttonOK.setBorderPainted(false);
        buttonOK.setContentAreaFilled(false);
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });
        getContentPane().add(buttonOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 120, 30));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/AddFriend.png"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    public void onOK() {

        String name = this.nameField.getText();

        if (name.isBlank()) {
            return;
        }

        PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

        if (!passwordConfirmation.getIsValid()) {
            return;
        }

        boolean res = this.client.requestFriend(name, passwordConfirmation.getName(), passwordConfirmation.getPassword());
        this.errorLabel.setVisible(true);

        if (res) {

            this.errorLabel.setText("Friend request done");
            this.nameField.setText("");

        } else {

            this.errorLabel.setText("Friend request error");

        }

    }

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        this.onOK();
    }//GEN-LAST:event_buttonOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOK;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel fondo;
    private javax.swing.JTextField nameField;
    // End of variables declaration//GEN-END:variables
}
