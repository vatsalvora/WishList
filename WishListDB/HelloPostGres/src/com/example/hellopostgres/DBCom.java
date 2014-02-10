//This file provides the libraries for using the WishList database. It will abstract the SQL, by creating simple java
//methods that will integrate with the final product.

package com.example.hellopostgres; //This line is for android functionality only. This is a test address. Change required based on final path.
import java.sql.*;

public class DBCom {
	
	String dburl = "jdbc:postgresql://98.180.57.56:5432/wishlist";
	String username = "wishlist_app";
	String password = "wl_app";
	String output = "";
	
	public String test() throws Exception{
		
		Class.forName("org.postgresql.Driver");
		
		DriverManager.setLoginTimeout(5);
		Connection connect = DriverManager.getConnection(dburl,username,password);
		Statement st = connect.createStatement();
		ResultSet resultSet = st.executeQuery("SELECT * FROM users");

		
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
	public void sendSQLnoReturn(String command){
		
		try{
		
		Class.forName("org.postgresql.Driver");
		DriverManager.setLoginTimeout(5);
		Connection connect = DriverManager.getConnection(dburl,username,password);
		Statement st = connect.createStatement();
		
		st.executeQuery(command);
		
		st.close();
        connect.close();
        
		} catch(Exception e){
			
		}
		
	}
	

}
