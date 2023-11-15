package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Chat;
import model.ListUser;
import model.User;
import observer.Observer;

public class App extends javax.swing.JFrame implements Observer {

    private final ClientImpl client;
    private User activeChatUser;

    public App(ClientImpl client) {

        initComponents();

        this.client = client;
        this.client.addObserver(this);

        this.tittleLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.tittleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.tittleLabel.setText("Hey " + client.getUser().getName());

        // Si se cierra deslogueamos al cliente
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.requestLogout();
                client.end();
                dispose();
            }
        });

        this.chatTable.getTableHeader().setVisible(false);

        this.noChatLabel.setVisible(true);

        this.updateConnectedFriends();
        this.updateFriendRequests();
        this.pack();
        this.setVisible(true);
        this.toFront();

    }

    public void updateFriendRequests() {

        this.numerOfFriendsRequests.setForeground(new java.awt.Color(255, 255, 255));
        this.numerOfFriendsRequests.setFont(new java.awt.Font("Segoe UI", 1, 18));
        this.numerOfFriendsRequests.setText("" + client.getUser().getFriendRequests().size());

    }

    public void updateConnectedFriends() {

        this.cfLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.cfLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
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
            this.friendList.clearSelection();

        }

        this.friendList.setModel(listModel);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        noChatLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        addFriendButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        tittleLabel = new javax.swing.JLabel();
        messageField = new javax.swing.JTextField();
        cfLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        numerOfFriendsRequests = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList<>();
        jTable = new javax.swing.JScrollPane();
        chatTable = new javax.swing.JTable();
        frButton = new javax.swing.JButton();
        removeFriendButton = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        noChatLabel.setBackground(new java.awt.Color(255, 255, 255));
        noChatLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/NoChat.png"))); // NOI18N
        noChatLabel.setOpaque(true);
        getContentPane().add(noChatLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 770, 520));
        getContentPane().add(errorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, -1, -1));

        sendButton.setBorder(null);
        sendButton.setBorderPainted(false);
        sendButton.setContentAreaFilled(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(sendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 570, 60, 50));

        addFriendButton.setBorder(null);
        addFriendButton.setBorderPainted(false);
        addFriendButton.setContentAreaFilled(false);
        addFriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(addFriendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 610, 240, 30));

        logoutButton.setBorder(null);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(logoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 80, 120, 30));

        tittleLabel.setBackground(new java.awt.Color(255, 255, 255));
        tittleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(tittleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 90, 20));

        messageField.setBackground(new java.awt.Color(45, 102, 212));
        messageField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        messageField.setForeground(new java.awt.Color(255, 255, 255));
        messageField.setText("Mensaje");
        messageField.setBorder(null);
        messageField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                messageFieldFocusGained(evt);
            }
        });
        getContentPane().add(messageField, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 585, 600, 20));

        cfLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(cfLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 140, 20));

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 430, 20));

        numerOfFriendsRequests.setBackground(new java.awt.Color(0, 0, 0));
        numerOfFriendsRequests.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        numerOfFriendsRequests.setForeground(new java.awt.Color(255, 255, 255));
        numerOfFriendsRequests.setToolTipText("");
        getContentPane().add(numerOfFriendsRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 570, 30, 20));

        friendList.setBackground(new java.awt.Color(220, 232, 255));
        friendList.setBorder(null);
        friendList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                friendListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(friendList);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 260, 400));

        jTable.setForeground(new java.awt.Color(220, 232, 255));

        chatTable.setBackground(new java.awt.Color(220, 232, 255));
        chatTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        chatTable.setFocusable(false);
        chatTable.setGridColor(new java.awt.Color(220, 232, 255));
        chatTable.setRequestFocusEnabled(false);
        chatTable.setRowSelectionAllowed(false);
        chatTable.setSelectionBackground(new java.awt.Color(220, 232, 255));
        chatTable.setSelectionForeground(new java.awt.Color(220, 232, 255));
        chatTable.setShowGrid(false);
        chatTable.getTableHeader().setResizingAllowed(false);
        chatTable.getTableHeader().setReorderingAllowed(false);
        chatTable.setUpdateSelectionOnSort(false);
        chatTable.setVerifyInputWhenFocusTarget(false);
        jTable.setViewportView(chatTable);

        getContentPane().add(jTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 710, 380));

        frButton.setBorder(null);
        frButton.setBorderPainted(false);
        frButton.setContentAreaFilled(false);
        frButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frButtonActionPerformed(evt);
            }
        });
        getContentPane().add(frButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 570, 250, 20));

        removeFriendButton.setBorder(null);
        removeFriendButton.setBorderPainted(false);
        removeFriendButton.setContentAreaFilled(false);
        removeFriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFriendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(removeFriendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 120, 120, 30));

        Fondo.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Chats.png"))); // NOI18N
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1270, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void frButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frButtonActionPerformed
        new FriendRequests(client);
    }//GEN-LAST:event_frButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String message = messageField.getText();

        if (message.isBlank()) {
            return;
        }

        this.client.sendMessage(activeChatUser, message);
        this.updateChat();

        this.messageField.setText("Message");

    }//GEN-LAST:event_sendButtonActionPerformed

    private void addFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendButtonActionPerformed

        new FriendRequest(client);

    }//GEN-LAST:event_addFriendButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed

        client.requestLogout();
        client.end();
        dispose();

    }//GEN-LAST:event_logoutButtonActionPerformed

    private void removeFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFriendButtonActionPerformed

        PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

        if (!passwordConfirmation.getIsValid()) {
            return;
        }
        boolean res = client.requestRemoveFriend(activeChatUser, passwordConfirmation.getName(), passwordConfirmation.getPassword());

        if (!res) {
            return;
        }

        this.activeChatUser = null;
        this.friendList.clearSelection();
        this.pack();
        this.setVisible(true);

    }//GEN-LAST:event_removeFriendButtonActionPerformed

    private void friendListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_friendListValueChanged

        ListUser listUser = friendList.getSelectedValue();

        this.noChatLabel.setVisible(listUser != null);

        if (listUser == null) {
            return;
        }

        this.activeChatUser = listUser.getUser();
        this.nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.nameLabel.setText(listUser.getUser().getName());

        updateChat();

    }//GEN-LAST:event_friendListValueChanged

    private void messageFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_messageFieldFocusGained

        this.messageField.setText("");

    }//GEN-LAST:event_messageFieldFocusGained

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton addFriendButton;
    private javax.swing.JLabel cfLabel;
    private javax.swing.JTable chatTable;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton frButton;
    private javax.swing.JList<ListUser> friendList;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jTable;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField messageField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel noChatLabel;
    private javax.swing.JLabel numerOfFriendsRequests;
    private javax.swing.JButton removeFriendButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel tittleLabel;
    // End of variables declaration//GEN-END:variables
}
