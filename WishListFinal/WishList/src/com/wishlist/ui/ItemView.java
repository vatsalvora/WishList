package com.wishlist.ui;

import java.util.ArrayList;

import com.wishlist.obj.WishItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.*;

public class ItemView extends Activity
{

    private TextView nameView;
    private TextView descriptionView;
    private TextView ownerView;
    private TextView buyerView;
    private TextView dateView;
    private TextView priceView;
    private ListView commentsView;
    private ImageView image;

    private ArrayList<String> comments;
    private ArrayAdapter<String> adapter;
    private Intent info;
    private WishItem item;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        nameView = (TextView) findViewById(R.id.nameView);
        descriptionView = (TextView) findViewById(R.id.description);
        commentsView = (ListView) findViewById(R.id.listOfComments);
        image = (ImageView) findViewById(R.id.ItemImage);

        comments = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        commentsView.setAdapter(adapter);
        
        info = getIntent();
        item =  info.getParcelableExtra("item");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_view, menu);
        return true;
    }
    
    private void displayItem(){
    	if(item == null){
    		throw new RuntimeException("WHAT?????? ITEM IS NULL! WTF!!!!");
    	}
    	
    	
    	
    }
}
