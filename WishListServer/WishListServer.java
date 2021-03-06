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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.sql.ResultSet;

public class WishListServer
{
    /* Control signals */
    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int USER_ADD = 2; //Add user to DB
    public static final int WISH_ADD = 3; //add wish to DB
    public static final int USER_SEND = 4; //Send user object to client (app)
    public static final int WISH_RM = 5;  //Delete wish from DB
    public static final int WISH_UP = 6;  //Update wish in DB
    public static final int IS_USER = 7;  //Check is user is in DB
    public static final int LIST_WISHES = 8; //Return wishes in DB owned by user
    public static final int STORE_IMAGE = 9;
    public static final int USERS_CHECK = 10;
    public static final int WISH_UPDATE = 11;
    public static final int BNAME_GET = 12;

    private ServerSocket server;
    private final int port = 5600;
    private Socket socket;
    private int currentWID;


    private boolean serverOn = true;

    private void updateCurrentWID()
    {
        DBCom dbtemp = new DBCom();
        currentWID = dbtemp.getCurrentMaxWID();
    }

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

        while(serverOn)
        {
            try
            {
                /*
                 * Listen for new connections, then spawn a new thread for
                 * each new connection.
                 *
                 * Control doesn't flow past this line until a new
                 * connection is made
                 */
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
        /** Thread that serves clients:
         *  grabs control signal (code)
         *  and acts accordingly
         */

        Socket clientSocket;
        boolean runThread = true;
        private DBCom db;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        private DataOutputStream dos;
        private DataInputStream dis;

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
                /* Init objects to transport data across network */

                /*
                 * Object IO streams are to transport objects
                 * Data IO streams are to transport simple data types (boolean)
                 */
                ois = new ObjectInputStream(
                    new BufferedInputStream(clientSocket.getInputStream()));

                oos = new ObjectOutputStream(
                    clientSocket.getOutputStream());

                dos = new DataOutputStream(
                    clientSocket.getOutputStream());

                dis = new DataInputStream(
                    clientSocket.getInputStream());




                boolean done = false;
                int code;
                String msg;

                //FBUser fb = new FBUser("0", "alex", false); //Just for testing
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
                            String name = (String)ois.readObject();
                            String uid = (String)ois.readObject();
                            db.addUser(uid, name);
                        }
                        else if (code == WISH_ADD)
                        {
                            String uid = (String)ois.readObject();
                            String name = (String)ois.readObject();
                            String descr = (String)ois.readObject();
                            String price = (String)ois.readObject();
                            db.addWish(uid, name, descr, price);
                        }
                        else if(code == WISH_RM)
                        {
                            String wid = (String)ois.readObject();
                            db.deleteWish(wid);
                        }
                        else if(code == LIST_WISHES)
                        {
                            String uid = (String)ois.readObject();
                            int numOfWishes = db.getNumOfWishes(uid);
                            oos.writeInt(numOfWishes);
                            oos.flush();
                            System.out.println("Sent numOfWishes: " + numOfWishes);

                            ResultSet resultSet = db.getWishes(uid);

                            try
                            {
                                while(resultSet.next())
                                {
                                    for(int i=1; i<=7; i++)
                                    {
                                        oos.writeObject(resultSet.getString(i));
                                        oos.flush();
                                    }
                                    oos.writeObject(resultSet.getDate(8));
                                    oos.flush();
                                }
                                

                            }
                            catch (Exception e)
                            {
                                //Error, add log here
                                System.out.println(e.toString());
                                System.out.println("You fd the list_wishes method.");

                            }
                            
                        }
                        else if(code == USERS_CHECK)
                        {
                            ArrayList<String> fbUsers =
                                (ArrayList<String>) ois.readObject();

                            ArrayList<String> WLUsers;
                            WLUsers = db.inDB(fbUsers);
                            oos.writeObject(WLUsers);
                            oos.flush();
                        }
                        else if(code == WISH_UPDATE)
                        {
							
							String wid = (String)ois.readObject();
							String bid = (String)ois.readObject();
                            String name = (String)ois.readObject();
                            String descr = (String)ois.readObject();
                            String price = (String)ois.readObject();
                            int status = ois.readInt();
                            db.updateWish(wid, bid, name, descr, price, status);
							
						}
						else if(code == BNAME_GET)
						{
							String wid = (String)ois.readObject();
							oos.writeObject(db.getBname(wid));
                            oos.flush();
						}
                        /*else if(code == USER_SEND)
                        {
                            oos.writeObject(fb);
                            oos.flush();
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
                        /*else if(code == STORE_IMAGE)
                        {
                                String name = (String)ois.readObject();
                                listenForImage(name);
                        }*/

                    }
                    catch(IOException ioe)
                    {
                        System.out.println("Client closed");
                        done = true;
                    }
                    catch (ClassNotFoundException e)
                    {
                        System.out.println(e.toString());
                        e.printStackTrace();
                        done = true;
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.toString());
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

        private void listenForImage(String imageName) throws Exception
        {

            /* Doesn't work yet, image gets corrupted */
            FileOutputStream fout = new FileOutputStream(imageName);

            int i;
            while ( (i = dis.read()) > -1)
            {
                fout.write(i);
            }

            fout.flush();
            fout.close();

        }


    }

}

