package com.wishlist.serv;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.wishlist.obj.FBUser;
import com.wishlist.obj.WishItem;

public class WLServerCom 
{
    private static int port = 5600;

    /* Alex's IP. Do not DDOS */
    private static String servIP = "98.180.57.56";

    protected InetAddress host;
    protected Socket socket;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;
    protected DataInputStream dis;

    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int USER_ADD = 2;
    public static final int WISH_ADD = 3;
    public static final int USER_SEND = 4;
    public static final int WISH_RM = 5;
    public static final int WISH_UP = 6;
    public static final int IS_USER = 7;
    public static final int LIST_WISHES = 8;

    public WLServerCom() throws UnknownHostException, IOException
    {
        
        //Create connection to server
        //For testing, server will be run on local host
        //host = InetAddress.getLocalHost();
        socket = new Socket(servIP, port);

        //Open an output stream
        oos = new ObjectOutputStream(
                socket.getOutputStream());

        //Open an input stream
        ois = new ObjectInputStream(
                socket.getInputStream());

        dis = new DataInputStream(
                socket.getInputStream());
     
    }

	//Should be private, but public for testing reasons
    public void sendObject(Object obj) throws IOException
    {
        oos.writeObject(obj);
        oos.flush();
    }

	//Should be private, but public for testing reasons
    public void sendCode(int code) throws IOException
    {
        oos.writeInt(code);
        oos.flush();
    }

	//Should be private, but public for testing reasons
    public Object getObject() throws IOException, ClassNotFoundException
    {
        return ois.readObject();
    }

    public void addUser(FBUser u) throws IOException
    {
        /** Adds given user to database */ 
        sendCode(USER_ADD);
        sendObject(u);
    }
    public void addWish(WishItem w) throws IOException
    {
        /** Adds given wish to database */
        sendCode(WISH_ADD);
        sendObject(w);
    }
    public void rmWish(String wid) throws IOException
    {
        /** Removes wish from db given the wish id */
        sendCode(WISH_RM);
        sendObject(wid);
    }
    public void updateWish(WishItem wi) throws IOException
    {
        /** Updates a wish in the db */
        rmWish(wi.getWID());
        addWish(wi);
    }
    public boolean isUser(String wid) throws IOException, ClassNotFoundException
    {
        /** Returns true if given user is in database */
        sendCode(IS_USER);
        sendObject(wid);
        return dis.readBoolean();
    }
    public ArrayList<WishItem> listWishes(String uid) throws IOException,
           ClassNotFoundException
    {
        /** Returns an ArrayList of wish objects that belong to the given
         * user 
         */
        sendCode(LIST_WISHES);
        sendObject(uid);

        /* Gives warning. TODO: make warning go away */
        return (ArrayList<WishItem>)getObject();
    }

}
