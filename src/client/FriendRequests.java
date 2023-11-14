package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import model.FriendRequest;
import model.User;
import observer.Observer;

enum Action {

    ACCEPT, DECLINE

}

public class FriendRequests extends javax.swing.JFrame implements Observer {

    private final ClientImpl client;

    public FriendRequests(ClientImpl client) {
        initComponents();
        this.client = client;
        this.client.addObserver(this);

        errorLabel.setOpaque(false);
        declineButton.setEnabled(false);
        this.getRootPane().setDefaultButton(acceptButton1);
        this.updateFriendRequests();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        this.pack();
        this.setVisible(true);
        this.toFront();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        declineButton = new javax.swing.JButton();
        acceptButton1 = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendRequestList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        declineButton.setBorder(null);
        declineButton.setBorderPainted(false);
        declineButton.setContentAreaFilled(false);
        declineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                declineButtonActionPerformed(evt);
            }
        });
        getContentPane().add(declineButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 420, 120, 30));

        acceptButton1.setBorder(null);
        acceptButton1.setBorderPainted(false);
        acceptButton1.setContentAreaFilled(false);
        acceptButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(acceptButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 120, 30));
        getContentPane().add(errorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 390, -1, -1));

        friendRequestList.setBackground(new java.awt.Color(220, 232, 255));
        jScrollPane1.setViewportView(friendRequestList);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 250, 230));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/FriendRequest.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void declineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_declineButtonActionPerformed
        this.requestAction(Action.DECLINE);
    }//GEN-LAST:event_declineButtonActionPerformed

    private void acceptButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButton1ActionPerformed
        this.requestAction(Action.ACCEPT);
    }//GEN-LAST:event_acceptButton1ActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton1;
    private javax.swing.JButton declineButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JList<FriendRequest> friendRequestList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
