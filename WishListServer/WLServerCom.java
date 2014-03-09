import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedInputStream;

public class WLServerCom {
    private static int port = 5600;

    protected InetAddress host;
    protected Socket socket;
    protected DataOutputStream dos;
    protected DataInputStream dis;


    public WLServerCom() throws UnknownHostException, IOException
    {
        
        //Create connection to server
        //For testing, server will be run on local host
        host = InetAddress.getLocalHost();
        socket = new Socket(host.getHostName(), port);

        //Open an output stream
        dos = new DataOutputStream(
                socket.getOutputStream());

        //Open an input stream
        dis = new DataInputStream(
                socket.getInputStream());
     
    }

    public void sendCMD(String cmd) throws IOException
    {
        dos.writeUTF(cmd);
        dos.flush();
    }

}
