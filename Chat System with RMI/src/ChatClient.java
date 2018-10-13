
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements Client {

    private Server server;
    private String name = null;
    private ClientGUI gui;

    protected ChatClient(String name, Server server) throws RemoteException {
        this.name = name;
        this.server = server;
        //server.connect(this);
        //server.sendMessage(name + " : has connected");
    }

    public String getName() {
        return name;
    }

    public void setGui(ClientGUI gui) {
        this.gui = gui;
    }


    public void receiveMessage(String message) throws RemoteException {
        //System.out.println(message);
        this.gui.ta_chat.append(message + "\n");
    }


    public void disconnect() throws RemoteException {
        //System.out.println(message);
        server.sendMessage(name + " has disconnected");
        server.disconnect(this);
    }

    public void connect() throws RemoteException {
        server.connect(this);
        server.sendMessage(name + " has connected");
    }



    //Send Message
    public void send(String message) throws RemoteException {

        server.sendMessage(name + ": " + message);
    }


}
