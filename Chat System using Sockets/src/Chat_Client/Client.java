package Chat_Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class Client extends JFrame {

    private JButton b_connect;
    private JButton b_disconnect;
    private JButton b_send;
    private JScrollPane jScrollPane1;
    private JLabel lb_address;
    private JLabel lb_port;
    private JLabel lb_username;
    private JTextArea ta_chat;
    private JTextField tf_address;
    private JTextField tf_chat;
    private JTextField tf_port;
    private JTextField tf_username;


    String username, address = "localhost";
    ArrayList<String> users = new ArrayList(); //The vector
    int port = 2222;
    Boolean isConnected = false;

    static Socket sock;
    static BufferedReader reader;
    static PrintWriter writer;


    //Constructor
    public Client(){
        //Set GUI Components
        initComponents();
    }

    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run() {

                Client client = new Client();
                client.setVisible(true);
                client.addWindowListener(new WindowAdapter() {
                    // extra check to properly close the socket if client closes dialog box without registering
                    public void windowClosing(WindowEvent e) {
                        try {
                            if(sock != null)
                                sock.close();
                            if(writer != null) {
                                writer.flush();
                                writer.close();
                            }
                            if (reader != null)
                                reader.close();

                        }
                        catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    private void initComponents() {

        lb_address = new JLabel();
        tf_address = new JTextField();
        lb_port = new JLabel();
        tf_port = new JTextField();
        lb_username = new JLabel();
        tf_username = new JTextField();
        b_connect = new JButton();
        b_disconnect = new JButton();
        jScrollPane1 = new JScrollPane();
        ta_chat = new JTextArea();
        tf_chat = new JTextField();
        b_send = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client");
        setName("client"); // NOI18N
        setResizable(false);

        lb_address.setText("Address : ");

        tf_address.setText("localhost");
        tf_address.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });

        lb_port.setText("Port :");

        tf_port.setText("");
        tf_port.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tf_portActionPerformed(evt);
            }
        });

        lb_username.setText("Username :");

        tf_username.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usernameActionPerformed(evt);
            }
        });


        b_connect.setText("Connect");
        b_connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    b_connectActionPerformed(evt);
                } catch (UnknownHostException e) {
                    ta_chat.append("Cannot Connect! Try Again. \n");
                    tf_username.setEditable(true);
                }
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });


        tf_chat.setEditable(false);
        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_send.setText("SEND");
        b_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
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
                                                .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lb_address, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(b_connect))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(66, 66, 66)
                                                                .addComponent(b_disconnect))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lb_port, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(17, 17, 17)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lb_address)
                                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_port)
                                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        pack();

    }

    private void tf_addressActionPerformed(ActionEvent evt) {
    }

    private void tf_portActionPerformed(ActionEvent evt) {
    }

    private void tf_usernameActionPerformed(ActionEvent evt) {
    }

    private void b_connectActionPerformed(ActionEvent evt) throws UnknownHostException {
        if (isConnected == false)
        {
            username = tf_username.getText();
            tf_username.setEditable(false);

            //Client is sending the MultiCast message
            final String INET_ADDR = "224.0.0.3";
            final int PORT = 6789;
            InetAddress addr = null;

            try {
                addr = InetAddress.getByName(INET_ADDR);

                DatagramSocket MultiCastSocket = null;
                MultiCastSocket = new DatagramSocket();

                //Send multicast message
                String msg = "Multicast message";

                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, addr, PORT); //dstaddr and port

                MultiCastSocket.send(msgPacket);

                System.out.println("Client sent packet with msg: " + msg);
                //Thread.sleep(500);

                //After sending the multicast the client will create a server socket and wait for the server to connect to it
                ServerSocket serverSock = new ServerSocket(MultiCastSocket.getLocalPort());
                sock = serverSock.accept();

                tf_port.setText(Integer.toString(MultiCastSocket.getLocalPort()));

                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
                tf_chat.setEditable(true);

                ListenThread();

            } catch (Exception e) {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }

            //ListenThread();

        }
        else if (isConnected == true)
        {
            ta_chat.append("You are already connected. \n");
        }
    }

    private void b_disconnectActionPerformed(ActionEvent evt) {
        sendDisconnect();
        Disconnect();
    }


    private void b_sendActionPerformed(ActionEvent evt) {
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
                writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new SocketServerConnection());
        IncomingReader.start();
    }

    public void userAdd(String data) {
        users.add(data);
    }

    public void userRemove(String data) {
        ta_chat.append(data + " is now offline.\n");
    }


    public void writeUsers()
    {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token:tempList)
        {
            //users.append(token + "\n");
        }
    }


    public void sendDisconnect()
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye);
            writer.flush();
        } catch (Exception e)
        {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }


    public void Disconnect()
    {
        try
        {
            tf_port.setText("");
            tf_username.setText("");
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);
        tf_chat.setEditable(false);
    }


    public class SocketServerConnection implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try
            {
                while ((stream = reader.readLine()) != null)
                {
                    data = stream.split(":");

                    if (data[2].equals(chat))
                    {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                    }
                    else if (data[2].equals(connect))
                    {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect))
                    {
                        userRemove(data[0]);
                    }
                    else if (data[2].equals(done))
                    {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                    }
                }
            }catch(Exception ex) { }
        }
    }


}
