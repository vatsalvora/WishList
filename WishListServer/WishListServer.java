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
import java.util.ArrayList;

public class WishListServer
{
    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int USER_ADD = 2;
    public static final int WISH_ADD = 3;
    public static final int USER_SEND = 4;
    public static final int WISH_RM = 5;
    public static final int WISH_UP = 6;
    public static final int IS_USER = 7;
    public static final int LIST_WISHES = 8;

    private ServerSocket server;
    private int port = 5600;
    private Socket socket;
    

    private boolean serverOn = true;

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
        //db = DBCom.instance();

        while(serverOn)
        {
            try
            {
                Socket clientSocket = server.accept();

                ClientServiceThread csThread = new ClientServiceThread(
                        clientSocket);
                csThread.start();
            }
            catch(IOException ioe)
            {
                System.out.println("Error occured on accept. Ignoring. trace:");
                ioe.printStackTrace();
            }
        }
        try
        {
            server.close();
            System.out.println("Server stopped");
        }
        catch (Exception e)
        {
            System.out.println("Problem stopping server socket");
            System.exit(-1);
        }

    }

    public static void main(String[] args)
    {
        new WishListServer();
    }

    class ClientServiceThread extends Thread
    {
        Socket clientSocket;
        boolean runThread = true;
        private DBCom db;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        private DataOutputStream dos;

        public ClientServiceThread()
        {
            super();
        }

        ClientServiceThread(Socket s)
        {
            clientSocket = s;
            db = new DBCom();
        }

        public void run()
        {
            /*
             * TODO, make this function shorter with some helper functions
             */
            System.out.println("Client accepted: " + clientSocket);

            try
            {
                ois = new ObjectInputStream(
                    new BufferedInputStream(clientSocket.getInputStream()));

                oos = new ObjectOutputStream(
                    clientSocket.getOutputStream());

                dos = new DataOutputStream(
                        clientSocket.getOutputStream());


                boolean done = false;
                int code;
                String msg;

                FBUser fb = new FBUser("0", "alex", false);
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
                        else if(code == USER_ADD)
                        {
                            FBUser tu = (FBUser)ois.readObject();
                            msg = tu.toString();
                            db.addUser(tu);
                            System.out.println(msg);
                        }
                        else if (code == WISH_ADD)
                        {
                            WishItem wi = (WishItem)ois.readObject();
                            msg = wi.toString();
                            db.addWish(wi);
                            System.out.println(msg);
                        }
                        else if(code == USER_SEND)
                        {
                            oos.writeObject(fb);
                            oos.flush();                     
                        }
                        else if(code == WISH_RM)
                        {
                            String wid = (String)ois.readObject();
                            db.deleteWish(wid);
                        }
                        else if(code == IS_USER)
                        {
                            String uid = (String)ois.readObject();
                            boolean isUser = db.isUser(uid);
                            dos.writeBoolean(isUser);
                            dos.flush();
                        }                    
                        else if(code == LIST_WISHES)
                        {
                            String uid = (String)ois.readObject();
                            ArrayList<WishItem> uWishes = db.listWishes(uid);
                            oos.writeObject(uWishes);
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
                clientSocket.close();
            }
        
               
            catch(IOException ioe)
            {
                System.out.println(ioe);
            }

        }
    }
    
}

