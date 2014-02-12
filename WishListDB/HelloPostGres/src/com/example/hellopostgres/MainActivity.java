//This file is used only for DATABASE TESTING PURPOSES. This file will not be intgrated
//into the WishList final product.

package com.example.hellopostgres;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
            	//mydb.sendSQLnoReturn("INSERT INTO fuck VALUES (234,'testworks')");
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