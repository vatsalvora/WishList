//This file provides the libraries for using the WishList database. It will abstract the SQL, by creating simple java
//methods that will integrate with the final product.

package com.example.hellopostgres; //This line is for android functionality only. This is a test address. Change required based on final path.
import java.sql.*;

import android.util.Log; //This needs to be imported to implement printing to logcat with thrown exceptions.

/* TODO: 
 * 
 * send SQL
 * 
 * 
 *  Get list of user wishes 
 *  Check if user is user (query by UID)
 * 	Add user
 *  remove user *later*
 *  Add wish
 *  remove wish
 *  set wish discription -- edit wish
 *  change wish status
 *  
 *  Do we want to return anything other than strings?
 *  Do we want to have ints as inputs to the methods? What does facebook return??!
 */

public class DBCom {
	
	String dburl = "jdbc:postgresql://98.180.57.56:5432/wishlist";
	String username = "wishlist_app";
	String password = "wl_app";
	String output = "";
	
	Connection connect;
	
	public DBCom(){
		
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
	
	//This method is simply sends a SQL Command to the database without expecting any return values.\
	//Not sure if this is the way we want to keep this.
	public boolean sendSQLnoReturn(String command){ //set to private after testing
		
		Statement st = null;
		
		boolean isOK = true;
		
		try{
		
		connect = DriverManager.getConnection(dburl,username,password);
		st = connect.createStatement();
		
		st.executeQuery(command); //Throws no return exception
        
		} 
		catch(Exception e){
			Log.e("Database",e.toString());
			isOK = false;
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
	
	//This method sends SQL and expects a resultset that it returns.
	//Make sure to CLOSE RESULTSET after using this method.
	//Maybe check if return value is NULL to avoid calling methods on nulls.
	
	//Result set will only return NULL when a problem occurs. If a query returns an empty set
	//the result set will be valid, just empty.
	//HOWEVER, If a command that inherently has no result set, such as INSERT, the method
	//WILL return null.
	public ResultSet sendSQLwithReturn(String command) {
		
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
	
}
