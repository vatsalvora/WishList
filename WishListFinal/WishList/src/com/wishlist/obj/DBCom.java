//This file provides the libraries for using the WishList database. It will abstract the SQL, by creating simple java
//methods that will integrate with the final product.

package com.wishlist.obj; //This line is for android functionality only. This is a test address. Change required based on final path.
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;

import android.util.Log; //This needs to be imported to implement printing to logcat with thrown exceptions.

/* DB Schema for reference
 * Users(int uid, string name)
 * wishes(int uid, int bid, String name, String descr, double price,
 *        int status, int wid)
 */

/* TODO: 
 * 
 *  Sync wish object with database
 *
 *  remove user *later*
 *  remove wish
 *  
 *  remove wid from methods - generate
 *  
 *  Do we want to return anything other than strings?
 *  Do we want to have ints as inputs to the methods? What does facebook return??!
 */

public class DBCom {
	
        /* Alex's ip. Please don't DDOS */
	String dburl = "jdbc:postgresql://98.180.57.56:5432/wishlist";
	String username = "wishlist_app";
	String password = "wl_app";
	String output = "";
	
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
	public static final String WHERE = "WHERE";
	Connection connect;
	
	public DBCom(){
		//This is the constructor for the Database Communication Object. An instance of this object is required to
		//use any coomunication methods. It provides the setup, linking the app with the postgres driver.
		
		try {
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e) {
			Log.e("Database",e.toString());
		}
		DriverManager.setLoginTimeout(5);
		
	}
	
	String test() throws Exception{
		
		Statement st;
		ResultSet resultSet;
		
		connect = DriverManager.getConnection(dburl,username,password);
		st = connect.createStatement();
		resultSet = st.executeQuery("SELECT * FROM users");

		
		while (resultSet.next()){
			for(int i = 1;i<3;i++){
				output = output + (resultSet.getString(i) + " ");
			}
			output = output + "\n";
		}
		
		resultSet.close();
        st.close();
        connect.close();
		
		return output;

	}
	
	public boolean sendSQLnoReturn(String command){ 
		//set to private after testing
		//This method is simply sends a SQL Command to the database without expecting any return values.\
		//Not sure if this is the way we want to keep this.
		
		Statement st = null;
		
		boolean isOK = true;
		
		try{
		
		    connect = DriverManager.getConnection(dburl,username,password);
		    st = connect.createStatement();
		    
		    st.executeQuery(command); //Throws no return exception
        
		} 
		catch(Exception e){
			Log.e("Database",e.toString());
			//This will throw an exception every time.
		}
		
		try {
			st.close();
			connect.close();
		} 
		catch (SQLException e) {
			Log.e("Database",e.toString());
			isOK = false;
		}
		
		return isOK;
		
	}
	
	public ResultSet sendSQLwithReturn(String command) {
		//This method sends SQL and expects a resultset that it returns.
		//Make sure to CLOSE RESULTSET after using this method.
		//Maybe check if return value is NULL to avoid calling methods on nulls.
		
		//Result set will only return NULL when a problem occurs. If a query returns an empty set
		//the result set will be valid, just empty.
		//HOWEVER, If a command that inherently has no result set, such as INSERT, the method
		//WILL return null.
		
		Statement st = null;
		ResultSet resultSet = null;

		try {

			connect = DriverManager.getConnection(dburl, username, password);
			st = connect.createStatement();

			resultSet = st.executeQuery(command); // Throws no return exception

		} 
		catch (Exception e) {
			Log.e("Database", e.toString());
		}

		try {
			//st.close();
			connect.close();
		} 
		catch (SQLException e) {
			Log.e("Database", e.toString());
		}
		return resultSet;

	}
	
	public boolean addUser(int uid, String name){
		
		String command = String.format("INSERT INTO users VALUES (%d, '%s')", uid, name); //Bad warning?
		return sendSQLnoReturn(command);
		
	}
	
	public boolean isUser(int uid) {

		String command = String.format("SELECT * FROM Users WHERE uid = %d", uid);
		ResultSet resultSet = sendSQLwithReturn(command);

		boolean isU = false;

		try {
		    if (resultSet.next()) {
			isU = true;
		    }
		} 
		catch (SQLException e) {
		    Log.e("Database", e.toString());
		}

		try {
		    resultSet.close(); //be sure that this isnt causing a mem leak because of statment not closing. Statement is however
		    //dereferenced.
		} 
		catch (SQLException e) {
		    Log.e("Database", e.toString());
		}

		return isU;
	}
	
        /* Instead of this method, couldn't we just create a wish object and
         * then do wish.commit() or wish.sync() or wish.sendToServer() or
         * whatever we call it instead?
         */
	public boolean addWish(int wid, int uid, String name, String descr, double price)
	{	
		if(price<0){
			String command = String.format("INSERT INTO wishes (wid, uid, name, descr, price) VALUES (%d, %d, '%s', '%s', NULL)",
					wid, uid, name, descr, price);
			return sendSQLnoReturn(command);
		}
		else{
			String command = String.format("INSERT INTO wishes (wid, uid, name, descr, price) VALUES (%d, %d, '%s', '%s', %f)",
											wid, uid, name, descr, price);
			return sendSQLnoReturn(command);
		}
	}
	
        public ArrayList<WishItem> listWishes(int uid){

            /** Returns an array of wish objects where the wishes belong
             * to the given user
             */

            ArrayList<WishItem> wishList = new ArrayList<WishItem>();
            String command = String.format("SELECT * FROM wishes WHERE uid=%d", uid); 
            ResultSet resultSet = sendSQLwithReturn(command);

            int bid, status, wid;
            String name, descr;
            double price;
            Date dateAdded;

            try {
                while(resultSet.next()) {
                    //uid is in col 1, but we already have that since it was passed
                    bid = resultSet.getInt(2);
                    name = resultSet.getString(3);
                    descr = resultSet.getString(4);
                    price = resultSet.getDouble(5);
                    status = resultSet.getInt(6);
                    wid = resultSet.getInt(7);
                    dateAdded = resultSet.getDate(8);

                    WishItem WI = new WishItem(uid, bid, name, descr, price,
                                                status, wid, dateAdded);
                    wishList.add(WI);

                }
            }
            catch (SQLException e) {
                Log.e("Database", e.toString());
            }
            
            return wishList;
        }

}
