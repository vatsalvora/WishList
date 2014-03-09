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

    private ServerSocket server;
    private int port = 5600;

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
        System.out.println("Waiting for client ...");
        try
        {
            Socket socket = server.accept();
            System.out.println("Client accepted: " + socket);

            ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(socket.getInputStream()));

            ObjectOutputStream oos = new ObjectOutputStream(
                socket.getOutputStream());


            boolean done = false;
            while (!done)
            {
                try
                {
                    String line = (String)ois.readObject();
                    System.out.println(line);
                    done = line.equals("bye");

                    if (line.equals("play"))
                    {
                        playClem();
                    }

                    if (line.equals("talk"))
                    {
                        oos.writeObject("I'm talking");
                        oos.flush();
                    }
                }
                catch(IOException ioe)
                {
                    System.out.println("Client closed");
                    done = true;
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

