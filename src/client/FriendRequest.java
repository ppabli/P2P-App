package client;

/**
 *
 * @author David
 */
public class FriendRequest extends javax.swing.JFrame {

    private final ClientImpl client;
    public FriendRequest(ClientImpl client) {
        initComponents();
        this.client = client;

        this.errorLabel.setOpaque(false);
        this.getRootPane().setDefaultButton(buttonOK);
        this.buttonOK.addActionListener(e -> onOK());
        
        this.pack();
        this.setVisible(true);
        this.toFront();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        nameField.setForeground(new java.awt.Color(0, 0, 0));
        nameField.setText("Usuario");
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

    public void onOK(){
        String name = this.nameField.getText();

        if (name.isBlank()) {
                return;
        }
        System.out.println("aaaaaaaa");
        PasswordConfirmation passwordConfirmation = new PasswordConfirmation(client);

        if (!passwordConfirmation.getIsValid()) {
            System.out.println("NOT VALID in FriendRequest.java");
                return;
        }
        System.out.println("ccccccccc");

        boolean res = this.client.requestFriend(name, passwordConfirmation.getName(), passwordConfirmation.getPassword());
        System.out.println("dddddd");
        this.errorLabel.setOpaque(true);

        if (res) {
                System.out.println("eeeeee");
                this.errorLabel.setText("Friend request done");
                this.nameField.setText("");

        } else {
                System.out.println("fffff");
                this.errorLabel.setText("Friend request error");

        }
    }
    
    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        // Ya se contempla con el listener en el propio constructor de la clase
    }//GEN-LAST:event_buttonOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOK;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel fondo;
    private javax.swing.JTextField nameField;
    // End of variables declaration//GEN-END:variables
}
