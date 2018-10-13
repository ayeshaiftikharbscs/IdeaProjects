package Chat_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {


    ArrayList clientOutputStreams;
    ArrayList<String> users;
    ArrayList<String> history;

    public Server() {

        invokeRegistrar();

        invokeAdmin();
    }

    public static void main(String args[]){

        Server server = new Server();
    }

    private void invokeRegistrar() {

        Thread starter = new Thread(new Server.SocketRegistrar());
        starter.start();

        System.out.println("Server started... \n");
    }

    private void invokeAdmin(){
        Thread admin= new Thread(new Server.AdministratorClient());
        admin.start();

        System.out.println("Administrator started... \n");
    }


    public void userAdd (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        System.out.println("Before " + name + " added. \n");

        users.add(name);
        System.out.println("After " + name + " added. \n");

        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message)
    {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext())
        {
            try
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                System.out.println("Sending: " + message + "\n");
                writer.flush();
            }
            catch (Exception ex)
            {
                System.out.println("Error telling everyone. \n");
            }
        }
    }

    private void addtoHistory(String message){

        if(history.size() != 10) {
            history.add(message);
        }
        else{
            history.remove(0);
            history.add(message);
        }

    }

    public class SocketRegistrar implements Runnable
    {
        @Override
        public void run()
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();
            history = new ArrayList();

            try
            {
                final  String INET_ADDR = "224.0.0.3";
                final int PORT = 6789;
                InetAddress address = InetAddress.getByName(INET_ADDR);
                byte[] buf = new byte[256];

                //ServerSocket serverSock = new ServerSocket(6789);

                //Receive MultiCast Message
                MulticastSocket MultiCastSocket = new MulticastSocket(PORT);
                MultiCastSocket.joinGroup(address);

                while (true)
                {
                    //Socket clientSock = serverSock.accept();

                    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                    MultiCastSocket.receive(msgPacket);
                    String msg = new String(buf, 0, buf.length);
                    System.out.println("Server received msg: " + msg);

                    //Connect Server to the Client
                    Socket clientSock = new Socket(msgPacket.getAddress(), msgPacket.getPort());//recv krty waqt is main ab source ki info hy

                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new Server.SocketUserThread(clientSock, writer));
                    listener.start();
                    System.out.println("Got a connection. \n");
                    //ta_chat.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                System.out.println("Error making a connection. \n");
                //ta_chat.append("Error making a connection. \n");
            }
        }
    }

    //ClientHandler
    public class SocketUserThread implements Runnable
    {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public SocketUserThread(Socket clientSocket, PrintWriter user)
        {
            client = user;
            try
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex)
            {
                System.out.println("Unexpected error... \n");
            }

        }

        @Override
        public void run()
        {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try
            {
                while ((message = reader.readLine()) != null)
                {
                    //ta_chat.append("Received: " + message + "\n");
                    data = message.split(":");

                    for (String token:data)
                    {
                        //ta_chat.append(token + "\n");
                        System.out.println(token + "\n");
                    }

                    if (data[2].equals(connect))
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);

                        addtoHistory(data[0] + " " + data[1]);
                        if(SavetoDB(data[0] + " " + data[1]) == 0){
                            System.out.println("Unable to save message to Database...\n");
                        }
                    }
                    else if (data[2].equals(disconnect))
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);

                        addtoHistory(data[0] + " has disconnected.");
                        if(SavetoDB(data[0] + " has disconnected.") == 0)
                            System.out.println("Unable to save message to Database...\n");
                    }
                    else if (data[2].equals(chat))
                    {
                        tellEveryone(message);

                        addtoHistory(data[0] + ": " + data[1]);
                        if(SavetoDB(data[0] + ": " + data[1]) == 0)
                            System.out.println("Unable to save message to Database...\n");
                    }
                    else
                    {
                        System.out.println("No Conditions were met. |n");
                        //ta_chat.append("No Conditions were met. \n");
                    }
                }
            }
            catch (Exception ex)
            {
                System.out.println("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }


    public class AdministratorClient implements Runnable{

        @Override
        public void run() {

            try
            {
                ServerSocket serverSock = new ServerSocket(9090);

                while (true)
                {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());

                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/html");
                    writer.println("\r\n");
                    writer.println("<h1> Last 10 Messages: </h1>");
                    for(String message : history){
                        writer.println("<p>" + message + " </p>");
                    }
                    writer.flush();

                    writer.close();
                    clientSock.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.out.println("Error making a connection. \n");
            }

        }
    }

    private int SavetoDB(String message) {
        int i = 0;
        try {
            Db_Connection dbconn = new Db_Connection();
            Connection myconnection = dbconn.Connection();

            PreparedStatement preparedStatement = myconnection.prepareStatement("INSERT INTO chatsystem.messages (message) VALUES (?)");
            preparedStatement.setString(1, message);

            i = preparedStatement.executeUpdate();

            preparedStatement.close();
            myconnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }



}
