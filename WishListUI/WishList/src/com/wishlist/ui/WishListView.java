package com.wishlist.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WishListView extends Activity {

	//Android components
	private TextView tv;
	private ListView lv;
	private Button b;
	
	//name of owner of list
	private String currentOwner;
	
	//list to hold WishItems
	private ArrayList<WishItem> wList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_view);
        
        tv = (TextView) findViewById(R.id.name_of_owner);
        lv = (ListView) findViewById(R.id.wishlist);
        b = (Button) findViewById(R.id.listOfPeople);
        
        tv.setText(currentOwner);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wish_list_view, menu);
        return true;
    }
    
}