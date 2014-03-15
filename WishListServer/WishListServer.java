/*
 * Shamelessly stolen from the internet and edited by Alex Bryan
 */
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class WishListServer
{
    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int FBUSER = 2;
    public static final int WISHITEM = 3;
    public static final int ECHO = 4;

    private ServerSocket server;
    private int port = 5600;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public WishListServer()
    {
        try
        {
            server = new ServerSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        WishListServer server = new WishListServer();
        server.connection();
    }

    public void playClem()
    { 
        /** Starts playing a song in my music player
         * Written to test if server accepts commands correctly
         */
        String cmd = "clementine -p";

        try
        {
            /*
             * Nothing to do with WL. No need to understand this line
             */
            Process pb = Runtime.getRuntime().exec(cmd);
        }
        catch (IOException ioe)
        {
            System.out.println("Something fucked up");
        }
    }


    public void connection()
    {
        /*
         * Does all server-side logic.
         * A huge mess, might as well just be main
         */
        System.out.println("Waiting for client ...");
        try
        {
            socket = server.accept();
            System.out.println("Client accepted: " + socket);

            ois = new ObjectInputStream(
                new BufferedInputStream(socket.getInputStream()));

            oos = new ObjectOutputStream(
                socket.getOutputStream());


            boolean done = false;
            int code;
            String msg;
            while (!done)
            {
                try
                {
                    code = ois.readInt();
                    System.out.println(code);

                    if(code == STRING)
                    {
                        msg = (String)ois.readObject();
                        System.out.println(msg);
                    }
                    else if(code == STRING_PLAY)
                    {
                        playClem();
                        msg = (String)ois.readObject();
                        System.out.println(msg);
                    }              

                    else if(code == FBUSER)
                    {
                        FBUser tu = (FBUser)ois.readObject();
                        msg = tu.toString();
                        System.out.println(msg);
                    }
                    else if (code == WISHITEM)
                    {
                        WishItem wi = (WishItem)ois.readObject();
                        msg = wi.toString();
                        System.out.println(msg);
                    }
                    else if(code == ECHO)
                    {
                        System.out.println("echo");
                        oos.writeObject("echo");
                        oos.flush();
                    }

                }
                catch(IOException ioe)
                {
                    System.out.println("Client closed");

                    System.out.println("Waiting for new client ...");
                    socket = server.accept();
                    System.out.println("Client accepted: " + socket);

                    ois = new ObjectInputStream(
                        new BufferedInputStream(socket.getInputStream()));

                    oos = new ObjectOutputStream(
                        socket.getOutputStream());

                    continue;
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                    done = true;
                } 
            }
            ois.close();
            oos.close();
            socket.close();
        }
                
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }

    }
}

