package com.wishlist.ui;

import java.util.ArrayList;

import com.wishlist.obj.WishItem;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.*;

public class ItemView extends Activity
{

    private TextView nameView;
    private TextView descriptionView;
    private TextView userView;
    private TextView buyerView;
    private TextView dateView;
    private TextView priceView;
    private ListView commentsView;
    private ImageView image;
    private Drawable picture;
    private ArrayList<String> comments;
    private ArrayAdapter<String> adapter;
    private Bundle info;
    private WishItem item;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        
        info = this.getIntent().getExtras();
        item = info.getParcelable(Transporter.WISH);
        
        nameView = (TextView) findViewById(R.id.name);
        descriptionView = (TextView) findViewById(R.id.description);
        userView = (TextView) findViewById(R.id.owner);
        buyerView = (TextView) findViewById(R.id.buyer);
        priceView = (TextView) findViewById(R.id.price);
        dateView = (TextView) findViewById(R.id.dateAdded);
        image = (ImageView) findViewById(R.id.ItemImage);

        comments = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        commentsView.setAdapter(adapter);
        
        displayItem();        
    }
    
    protected void onStart(){
    	super.onStart();
    }
    
    protected void onRestart(){
    	super.onRestart();
    }
    
    protected void onResume(){
    	super.onResume();
    }
    
    protected void onPause(){
    	super.onPause();
    }
    
    protected void onStop(){
    	super.onStop();
    }
    
    protected void onDestroy(){
    	super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_view, menu);
        return true;
    }
    
    public void displayItem(){
    	if(item == null) throw new RuntimeException("WHAT?????? ITEM IS NULL! WTF!!!!");
    	nameView.setText("Name: "+item.getName());
    	descriptionView.setText("Description: "+item.getDescription());
    	userView.setText("User: "+item.getUserName());
    	buyerView.setText("Buyer: "+item.getBuyerName());
    	priceView.setText("Price: "+item.getPrice());
    	dateView.setText("Date Added: "+item.getDate().toString());
    	image.setImageDrawable(picture);
    }
}
