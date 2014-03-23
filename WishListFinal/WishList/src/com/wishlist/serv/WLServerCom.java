package com.wishlist.serv;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import android.util.Log;

import com.wishlist.obj.FBUser;
import com.wishlist.obj.WishItem;

public final class WLServerCom 
{
    private static int port = 5600;

    /* Alex's IP. Do not DDOS */
    private static String servIP = "98.180.57.56";

    protected static InetAddress host;
    protected static Socket socket;
    protected static ObjectOutputStream oos;
    protected static ObjectInputStream ois;
    protected static DataInputStream dis;
    protected static DataOutputStream dos;

    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int USER_ADD = 2;
    public static final int WISH_ADD = 3;
    public static final int USER_SEND = 4;
    public static final int WISH_RM = 5;
    public static final int WISH_UP = 6;
    public static final int IS_USER = 7;
    public static final int LIST_WISHES = 8;
    public static final int STORE_IMAGE = 9;

    public static void init() throws UnknownHostException, IOException{
    	 //Create connection to server
        //For testing, server will be run on local host
        //host = InetAddress.getLocalHost();
    	Log.e("Socket","Got Here!");
        socket = new Socket(servIP, port);
        Log.e("Socket",socket.getPort()+"");
        //Open an output stream
        oos = new ObjectOutputStream(
                socket.getOutputStream());

        //Open an input stream
        ois = new ObjectInputStream(
                socket.getInputStream());

        dis = new DataInputStream(
                socket.getInputStream());
        
        dos = new DataOutputStream(
        		socket.getOutputStream());
    }
    
    private WLServerCom() throws UnknownHostException, IOException
    {
        init();    
    }

	//Should be private, but public for testing reasons
    public static void sendObject(Object obj) throws IOException
    {
        oos.writeObject(obj);
        oos.flush();
    }

	//Should be private, but public for testing reasons
    public static void sendCode(int code) throws IOException
    {
        oos.writeInt(code);
        oos.flush();
    }

	//Should be private, but public for testing reasons
    public static Object getObject() throws IOException, ClassNotFoundException
    {
        return ois.readObject();
    }

    public static void addUser(FBUser u) throws IOException
    {
        /** Adds given user to database */ 
        sendCode(USER_ADD);
        sendObject(u);
    }
    public static void addWish(WishItem w) throws IOException
    {
        /** Adds given wish to database */
        sendCode(WISH_ADD);
        sendObject(w);
    }
    public static void rmWish(String wid) throws IOException
    {
        /** Removes wish from db given the wish id */
        sendCode(WISH_RM);
        sendObject(wid);
    }
    public static void updateWish(WishItem wi) throws IOException
    {
        /** Updates a wish in the db */
        rmWish(wi.getWID());
        addWish(wi);
    }
    public static boolean isUser(String uid) throws IOException, ClassNotFoundException
    {
		//Is this supposed to be UID? -- Will
		//yes -- Alex
        /** Returns true if given user is in database */
        sendCode(IS_USER);
        sendObject(uid);
        return dis.readBoolean();
    }
    @SuppressWarnings("unchecked")
	public static ArrayList<WishItem> listWishes(String uid) throws IOException,
           ClassNotFoundException
    {
        /** Returns an ArrayList of wish objects that belong to the given
         * user 
         */
        sendCode(LIST_WISHES);
        sendObject(uid);

        return (ArrayList<WishItem>)getObject();
    }
    
    //Primative method to store image in the database.
    //To do : make it extract the name from the path itself -- WILL NEIL
    public static void storeImage(String name, String path) throws IOException, ClassNotFoundException
    {
    	sendCode(STORE_IMAGE);
    	sendObject(name);
    	
    	int i;
		FileInputStream fis = new FileInputStream(path);
		while ((i = fis.read()) > -1)
			dos.write(i);

		fis.close();
    }

}
