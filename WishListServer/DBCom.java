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
    public static final String ORDER_BY = "ORDER BY";

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

        System.out.println("W/O return \n" + command);

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

        System.out.println("With ret \n" + command);
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

    private static String tupleBuilder(String... in)
    {
        String tuple="(";
        for(int i=0; i<in.length; i++)
        {
            tuple += i;
            if(i < in.length-1) tuple += ", ";
        }
        tuple += ")";
        return tuple;
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
        /* Do we need to keep this method around ? HELL YES*/
//        command = queryBuilder(INSERT, INTO, "users", VALUES, uid, name);
        command = String.format("INSERT INTO users (uid, name) VALUES ('%s', '%s')",
                                uid, name);
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
    public boolean addWish( String uid, String name, String descr, String price)
    {
        String tuple = String.format("('%s', '%s', '%s', '%s', now())",uid, name, descr, price);
        command = queryBuilder(INSERT, INTO, "wishes", "(uid, name, descr, price, dateAdded)", VALUES, tuple);
        return sendSQLnoReturn(command);
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
        return sendSQLnoReturn(command);
    }

    public int getCurrentMaxWID()
    {

        command = queryBuilder(SELECT, "wid", FROM, "wishes", "ORDER BY", "wid", "DESC", "LIMIT 1");
        ResultSet resultSet = sendSQLwithReturn(command);

        try
        {
            if(resultSet.next())
            {
                String result = resultSet.getString(1);
                resultSet.close();
                return Integer.parseInt( result );
            }
            else
            {
                return 0;
            }
        }
        catch (Exception e)
        {
            //Error, add log here
            return 0;
        }

    }

    public int getNumOfWishes(String uid)
    {

        //command = queryBuilder(SELECT, "count(wid)", FROM, "wishes", WHERE, "uid=", "'", uid,"'", "GROUP BY", "uid");
        command = String.format("SELECT count(wid) FROM wishes WHERE uid = '%s' GROUP BY uid", uid);
        ResultSet resultSet = sendSQLwithReturn(command);

        try
        {
            if(resultSet.next())
            {
                String result = resultSet.getString(1);
                resultSet.close();
                return Integer.parseInt( result );
            }
            else
            {
                return 0;
            }
        }
        catch (Exception e)
        {
            //Error, add log here
            return 0;
        }

        /*
        SELECT count(wid)
        FROM wishes
        WHERE uid = '13'
        GROUP BY uid;
        */

    }

    public ResultSet getWishes(String uid)
    {

        command = "SELECT * FROM wishes WHERE uid = '" + uid + "'";
        ResultSet resultSet = sendSQLwithReturn(command);
        return resultSet;

    }


}
