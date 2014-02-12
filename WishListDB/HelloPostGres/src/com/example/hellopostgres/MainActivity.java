//This file is used only for DATABASE TESTING PURPOSES. This file will not be intgrated
//into the WishList final product.

package com.example.hellopostgres;

import java.sql.ResultSet;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView resultArea;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultArea = new TextView(this);
        resultArea.setText("Please wait.");
        setContentView(resultArea);
        
        new FetchSQL().execute();
    }
    
    private class FetchSQL extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
        	
        	DBCom mydb = new DBCom();
            try {
//            	ResultSet fun = mydb.sendSQLwithReturn("INSERT INTO users VALUEs (12,'test2')");
//            	if(fun==null){
//            		Log.e("Database", "The NULL query returned NULL");
//            	} 
//            	else{
//            		Log.e("Database", "The NULL query DID NOT return NULL");
//            	}
            	//mydb.addUser(37, "Schwartz");
            	if(mydb.isUser(37)){
            		Log.e("Database","Schwartz IS a USER! WOOP!");
            	}
            	else {
            		Log.e("Database","Swartz is NOT a user" );            		
            	}
				return mydb.test();
			} catch (Exception e) {
				return e.toString();
			}
        }
        
        @Override
        protected void onPostExecute(String value) {
            resultArea.setText(value);
        }
    }
}