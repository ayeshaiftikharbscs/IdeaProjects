
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer extends UnicastRemoteObject implements Server{

    private static final long serialVersionID = 1L;
    ArrayList<String> history;

    //get list of clients
    public ArrayList<Client> getChatClients() {
        return chatClients;
    }

    private ArrayList<Client> chatClients; //remote interface

    protected ChatServer() throws RemoteException {
       chatClients = new ArrayList<Client>();

       history = new ArrayList<String>();
       invokeAdmin();
    }

    public synchronized void connect(Client clientIF) throws RemoteException {

        this.chatClients.add(clientIF);
    }

    public synchronized void disconnect(Client clientIF) throws RemoteException {
        this.chatClients.remove(clientIF);
    }

    public synchronized void sendMessage(String message) throws RemoteException {

        addtoHistory(message);

        if(SavetoDB(message) == 0)
            System.out.println("Unable to save message to Database...\n");

        for(int i=0; i< chatClients.size(); i++){
            chatClients.get(i).receiveMessage(message);
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

    public static void main(String[] args) throws RemoteException, MalformedURLException {

        Server server = new ChatServer();
        Naming.rebind("RMIChatServer", server);

        System.out.println("Server started..");

    }

    private void invokeAdmin(){
        Thread admin= new Thread(new ChatServer.AdministratorClient());
        admin.start();

        System.out.println("Administrator started... \n");
    }

    public class AdministratorClient implements Runnable{

        @Override
        public void run() {

            try
            {
                ServerSocket serverSock = new ServerSocket(9595);

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
                System.out.println("Error making a connection. \n");
                //ta_chat.append("Error making a connection. \n");
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
