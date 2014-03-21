/*
 * Testing git commit - Linux Command Line
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
    public static final int RECIEVE_IMAGE = 9;

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
            updateCurrentWID(); 
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
                    
                


                boolean done = false;
                int code;
                String msg;

                FBUser fb = new FBUser("0", "alex", false); //Just for testing
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
                            
                            
                            String widTemp = wi.getWID();
                            if(widTemp.equals("") || widTemp == null)
							{
                            	currentWID++;
                            	wi.setWID(Integer.toString(currentWID));
                            }
                            
                            
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
                        else if(code == RECIEVE_IMAGE)
                        {
                        	String name = (String)ois.readObject();
                        	listenForImage(name);                       	
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
                    catch (Exception e)
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
        
        private void listenForImage(String imageName) throws Exception 
        {

    		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
    		FileOutputStream fout = new FileOutputStream(imageName);
    		
    		int i;
    		while ( (i = dis.read()) > -1) {
    			fout.write(i);
    		}
    		
    		fout.flush();
    		fout.close();
    		dis.close();
    		
    	}
        
        
    }
    
}

