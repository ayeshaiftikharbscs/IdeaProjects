import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db_Connection {

    public Connection Connection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatsystem?verifyServerCertificate=false&useSSL=true","root","goodradio99");

            return myConnection;

        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Db_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
