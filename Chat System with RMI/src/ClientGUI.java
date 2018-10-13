

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientGUI extends JFrame {

    private JButton b_connect;
    private JButton b_disconnect;
    private JButton b_send;
    private JScrollPane jScrollPane1;
    private JLabel lb_username;
    JTextArea ta_chat;
    JTextField tf_chat;
    private JTextField tf_username;

    static ChatClient client;
    static Server server;
    Boolean isConnected = false;

    public static void main(String[] args)  {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ClientGUI clientGUI = new ClientGUI();
                clientGUI.setVisible(true);
                clientGUI.addWindowListener(new WindowAdapter() {

                    // extra check to properly close connection if client closes dialog box without registering
                    public void windowClosing(WindowEvent e) {
                        try {
                            if(server != null)
                                server.disconnect(client);
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    ClientGUI(){
        initComponents();
    }

    private void initComponents() {

        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client"); // NOI18N
        setResizable(false);

        lb_username.setText("Username :");

        tf_username.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
            }
        });

        b_connect.setText("Connect");
        b_connect.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    b_connectActionPerformed(evt);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        tf_chat.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_chatActionPerformed(evt);
            }
        });

        b_send.setText("SEND");
        b_send.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(113, 113, 113)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lb_username, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(b_connect)
                                                                .addGap(35, 35, 35)
                                                                .addComponent(b_disconnect)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                                                        .addComponent(jScrollPane1))
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tf_username)
                                        .addComponent(lb_username))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(b_connect)
                                        .addComponent(b_disconnect))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_chat)
                                        .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                                .addGap(22, 22, 22))
        );

        tf_chat.setEditable(false);

        pack();
    }


    private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {

    }
    private void tf_chatActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) throws RemoteException, NotBoundException, MalformedURLException {
        if (isConnected == false) {
            tf_username.setEditable(false);

            try {
                isConnected = true;
                String serverURL = "rmi://localhost/RMIChatServer";
                server = (Server) Naming.lookup(serverURL);
                client = new ChatClient(tf_username.getText(), server);
                client.setGui(this);
                client.connect();

                tf_chat.setEditable(true);
            } catch (Exception ex) {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }

        }
        else if (isConnected == true)
        {
            ta_chat.append("You are already connected. \n");
        }
    }

    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {
        try
        {
            tf_username.setText("");
            client.disconnect();
            ta_chat.append("Disconnected.\n");
        } catch(Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);
        tf_chat.setEditable(false);
    }

    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {

        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        }
        else {
            try {
                client.send(tf_chat.getText());
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();

    }


}