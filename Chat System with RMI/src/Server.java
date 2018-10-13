
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    void connect(Client clientIF) throws RemoteException;
    void disconnect(Client clientIF) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
}
