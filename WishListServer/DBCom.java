/*This file provides the libraries for using the WishList database. 
 * It will abstract the SQL, by creating simple java methods that will integrate with the final product.
* 
* Change: Implemented the Singleton design pattern by Joon Kim
* Reason: We will only have one DBCom object in the app at a time, so the design pattern here is appropriate.
*/

import java.sql.*;
import java.util.ArrayList;

//import com.wishlist.obj.User;
//import com.wishlist.obj.WishItem;


public class DBCom
{

    private static DBCom _instance = null;
        
    /* Alex's ip. Please don't DDOS */
    //private String dburl = "jdbc:postgresql://98.180.57.56:5432/wishlist";
    private String dburl = "jdbc:postgresql://localhost/wishlist";
    private String username = "wl_app";
    private String password = "wl_app";
    private String command;
    private String output = "";

    public static final String SELECT = "SELECT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String INSERT = "INSERT";
    public static final String INTO = "INTO";
    public static final String CREATE = "CREATE";
    public static final String ALTER = "ALTER";
    public static final String DATABASE = "DATABASE";
    public static final String DROP = "DROP";
    public static final String TABLE = "TABLE";
    public static final String INDEX = "INDEX";
    public static final String FROM = "FROM";
    public static final String VALUES = "VALUES";
    public static final String WHERE = "WHERE";
    public static final String ALL = "*";
    public static final String SET = "SET";

    private Connection connect;
    
    public DBCom()
    {
        //This is the constructor for the Database Communication Object. An instance of this object is required to
        //use any coomunication methods. It provides the setup, linking the app with the postgres driver.

        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Database\t" + e.toString());
        }
        DriverManager.setLoginTimeout(5);

    }

    String test() throws Exception
    {

        Statement st;
        ResultSet resultSet;

        connect = DriverManager.getConnection(dburl,username,password);
        st = connect.createStatement();
        resultSet = st.executeQuery("SELECT * FROM users");


        while (resultSet.next())
        {
            for(int i = 1; i<3; i++)
            {
                output += (resultSet.getString(i) + " ");
            }
            output += "\n";
        }

        resultSet.close();
        st.close();
        connect.close();

        return output;

    }

    public boolean sendSQLnoReturn(String command)
    {
        /** This method is simply sends a SQL Command to the database
         * without expecting any return values
         *
         * Returns true if SQL
         * executed without any errors.
         */
        //set to private after testing
        //Not sure if this is the way we want to keep this.

        Statement st = null;

        boolean isOK = true;

        try
        {

            connect = DriverManager.getConnection(dburl,username,password);
            st = connect.createStatement();

            st.executeQuery(command); //Throws no return exception

        }
        catch(Exception e)
        {
            System.out.println("Database\t" + e.toString());
            //This will throw an exception every time.
        }

        try
        {
            st.close();
            connect.close();
        }
        catch (SQLException e)
        {
            System.out.println("Database\t" + e.toString());
            isOK = false;
        }

        return isOK;

    }

    public ResultSet sendSQLwithReturn(String command)
    {
        /** This method sends SQL and expects a resultset that it returns. */

        //Make sure to CLOSE RESULTSET after using this method.
        //Maybe check if return value is NULL to avoid calling methods on nulls.

        //Result set will only return NULL when a problem occurs. If a query returns an empty set
        //the result set will be valid, just empty.
        //HOWEVER, If a command that inherently has no result set, such as INSERT, the method
        //WILL return null.

        Statement st = null;
        ResultSet resultSet = null;

        try
        {

            connect = DriverManager.getConnection(dburl, username, password);
            st = connect.createStatement();

            resultSet = st.executeQuery(command); // Throws no return exception

        }
        catch (Exception e)
        {
            System.out.println("Database\t" + e.toString());
        }

        try
        {
            //st.close();
            connect.close();
        }
        catch (SQLException e)
        {
            System.out.println("Database\t" + e.toString());
        }
        return resultSet;

    }

    //please use this method to build queries (Joon)
    private static String queryBuilder(String... in)
    {
        String query = "";
        for(String i : in)
        {
            query += i + " ";
        }
        return query;
    }
    
    //this too (Joon)
    private static void queryAppend(String toAppend, String... in)
    {
        for(String i : in)
        {
                toAppend += i + " ";
        }
        toAppend += "\n";
    }
    
    public boolean addUser(String uid, String name)
    {
        /* Do we need to keep this method around ? */
        command = queryBuilder(INSERT, INTO, "users", VALUES, uid, name);
        return sendSQLnoReturn(command);

    }

    public boolean addUser(User u)
    {

        /** Adds user to database */

        String uName = "\'" + u.getName() + "\'";
        String uid = "\'" + u.getUID() + "\'";

        System.out.println(uName);
        System.out.println(uid);

        /*
         * Using this query builder doesn't work. Suck it Joon
         */
        //command = queryBuilder(INSERT, INTO, "users", VALUES, uid, uName);

        command = String.format("INSERT INTO users (uid, name) VALUES (%s, %s)",
                uid, uName);
        
        return sendSQLnoReturn(command);
    }

    public boolean isUser(String uid)
    {

        /** Returns true if given uid is in database */
        
        String uidStr = "\'" + uid + "\'";
        command = queryBuilder(SELECT, ALL, FROM, "users", WHERE, "uid =", uidStr);
        ResultSet resultSet = sendSQLwithReturn(command);

        boolean isU = false;

        try
        {
            if (resultSet.next())
            {
                isU = true;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database\t" +  e.toString());
        }

        try
        {
            resultSet.close(); //be sure that this isnt causing a mem leak because of statment not closing. Statement is however
            //dereferenced.
        }
        catch (SQLException e)
        {
            System.out.println("Database\t" +  e.toString());
        }

        return isU;
    }

    /* Instead of this method, couldn't we just create a wish object and
     * then do wish.commit() or wish.sync() or wish.sendToServer() or
     * whatever we call it instead?
     */
    public boolean addWish(String wid, String uid, String name, String descr, String price)
    {
       String tuple = "("+wid+", "+uid+", "+name+", "+descr+", "+price+")";
       command = queryBuilder(INSERT, INTO, "wishes", "(wid, uid, name, descr, price)", VALUES, tuple);
       return sendSQLnoReturn(command);
    }

    public boolean addWish(WishItem wi)
    {

        /** Adds a wish to the DB given a WishItem object */

        /* WARNING:
         *
         * This doesn't not check to see if any of the values are NULL
         *
         * This method doesn't yet support the dateAdded timestamp.
         * You can only pass strings to the database and I don't know
         * if Date.toString() produces a string that is compatible with
         * the postgres timestamp type
         */

        String uid = wi.getUID();
        String wid = wi.getWID();
        String name = wi.getName();
        String bid = wi.getBID();
        
        String descr = wi.getDescription();
        String price = wi.getPrice();
        int status = wi.getStatus();

        /* Special handling incase bid or priceStr is NULL.
         * Since the SQL commands are all strings anyway, we can convert
         * our ints and doubles to strings. This gives us the added
         * benefit of specifiying a value is NULL by giving it NULL.
         *
         * As long as we don't wrap our strong in single quotes(' '),
         * SQL will convert it to the proper data types
         */

        String priceStr;

        if(descr == "")
        {
            descr = "NULL";
        }

        if (bid == null)
        {
            bid = "NULL";
        }
        

        /*
         * Not sure if all of these are necessary.
         * The thrid one (price.equals("")) is necessary for sure
         *
         * Price is represented by the money type in the db.
         * Inserting '' for a money type defaults to $0.00.
         * Is this what we want?
         */
        if (price == "0.00")
        {
            priceStr = "\'\'";
        }
        if(price == null)
        {
            priceStr = "\'\'";
        }
        if(price.equals(""))
        {
            priceStr = "\'\'";
        }


        else
        {
            priceStr = price;
        }
        
        String tuple = String.format("('%s', '%s', '%s','%s', %s, %d, '%s')",
                                      uid, bid, name, descr, priceStr, status, wid);

        command = queryBuilder(INSERT, INTO, "wishes", "(uid, bid, name, descr, price, status, wid)", "VALUES", tuple);
        System.out.println(command);

        return sendSQLnoReturn(command);


    }




    public ArrayList<WishItem> listWishes(String uid)
    {

        /** Returns an array of wish objects where the wishes belong
         * to the given user
         */

        String uidStr = "\'" + uid + "\'";

        ArrayList<WishItem> wishList = new ArrayList<WishItem>();
        command = queryBuilder(SELECT, ALL, FROM, "wishes", WHERE, "uid=", uidStr);
        ResultSet resultSet = sendSQLwithReturn(command);

        String bid, wid;
        String name, descr;
        int status;
        String price;
        Date dateAdded;

        try
        {
            while(resultSet.next())
            {
                //uid is in col 2, but we already have that since it was passed
                
                /* What happens when value in DB is NULL ? */
                //Answer: Throw an exception. (Joon)
                wid = resultSet.getString(1);
                if(wid == null) throw new RuntimeException("Item not in database!");
                bid = resultSet.getString(3);
                name = resultSet.getString(4);
                descr = resultSet.getString(5);
                price = resultSet.getString(6);
                status = resultSet.getInt(7);
                dateAdded = resultSet.getDate(8);
                


                WishItem WI = new WishItem(uid, bid, name, descr, price,
                                           status, wid, dateAdded);
                wishList.add(WI);

            }
        }
        catch (SQLException e)
        {
            System.out.println("Database\t" +  e.toString());
        }

        return wishList;
    }

    public boolean deleteWish(String wid)
    {
        /** Deletes a wish from the database given the wid */

        /* This only deletes the DB entry for this wish.  This does not
         * delete a local copy of the wish and does not remove the wish
         * from the wList object in the User class
         */
        
        String widStr = "\'" + wid + "\'";
        command = queryBuilder(DELETE, FROM, "wishes", WHERE, "wid=", widStr);
        System.out.println(command);
        return sendSQLnoReturn(command);
    }

    public boolean updateWish(WishItem wi)
    {
        /** Updates the database entry for the given Wish
         * Returns true if operation was successful, false if not
         */

        /* This method assumes that an update needs to happen and thus
         * does not check to see if values provided by WishItem object
         * already match DB entry (Wait, why not?, says Joon)
         * Joon: Let's be more efficient.
         */

        /*
         * Doesn't work. Will work on later.
         */
        
        command = queryBuilder(UPDATE, "wishes");
        
        switch(wi.getUpdate())
        {
                case WishItem.NONE:
                        return false;
                case WishItem.BUYER:
                        queryAppend(command, "SET", "bid=", wi.getBID(), "bname", wi.getBuyerName());
                        break;
                case WishItem.DATE:
                        queryAppend(command, "SET", "date=", wi.getDate().toString());
                        break;
                case WishItem.DESC:
                        queryAppend(command, "SET", "descr=", wi.getDescription());
                        break;
                case WishItem.PICTURE:
                        throw new UnsupportedOperationException();
                case WishItem.PRICE:
                        queryAppend(command, "SET", "price=", wi.getPrice());
                        break;
                case WishItem.USER:
                        queryAppend(command, "SET", "uid=", wi.getUID(), "uname", wi.getUserName());
                        break;
                case WishItem.WISH:
                        queryAppend(command, "SET", "name=", wi.getName());
                        break;
                case WishItem.STATUS:
                        queryAppend(command, "SET", "status=", Integer.toString(wi.getStatus()));
                        break;
                default:
                        throw new RuntimeException("WTF is this? This is not supported...");
        }
        queryAppend(command, "WHERE", "wid=", wi.getWID());
        System.out.println(command);
        return sendSQLnoReturn(command);
    }
   

   
}
