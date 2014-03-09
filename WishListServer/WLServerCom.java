import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class WLServerCom {
    private static int port = 5600;

    protected InetAddress host;
    protected Socket socket;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;


    public WLServerCom() throws UnknownHostException, IOException
    {
        
        //Create connection to server
        //For testing, server will be run on local host
        host = InetAddress.getLocalHost();
        socket = new Socket(host.getHostName(), port);

        //Open an output stream
        oos = new ObjectOutputStream(
                socket.getOutputStream());

        //Open an input stream
        ois = new ObjectInputStream(
                socket.getInputStream());
     
    }

    public void sendCMD(String cmd) throws IOException
    {
        oos.writeObject(cmd);
        oos.flush();
    }

}
