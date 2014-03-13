package com.wishlist.ui;

import java.util.ArrayList;

import com.wishlist.obj.WishItem;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ItemView extends Activity
{
	
	public static final int NAME = 0;
	public static final int USER = 1;
	public static final int BUYER = 2;
	public static final int DATE = 3;
	public static final int DESC = 4;
	public static final int PRICE = 5;
	
	private TextView v[] = new TextView[PRICE+1];
	
    private ListView commentsV;
    private ImageView imageV;
    
    private ArrayList<String> comments;
    private ArrayAdapter<String> adapter;
    private WishItem item;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        
        v[NAME] = (TextView) findViewById(R.id.name);
		v[DESC] = (TextView) findViewById(R.id.description);
		v[USER] = (TextView) findViewById(R.id.owner);
		v[BUYER] = (TextView) findViewById(R.id.buyer);
		v[PRICE] = (TextView) findViewById(R.id.price);
		v[DATE] = (TextView) findViewById(R.id.dateAdded);
		
		comments = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
		commentsV.setAdapter(adapter);
        
    }
    
    protected void onStart(){
    	super.onStart();
    	displayItem();
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
    
    protected void displayItem(){
    	item = (WishItem) Transporter.unpackObject(this.getIntent().getExtras(), Transporter.WISH);
    	if(item == null) throw new RuntimeException("WHAT?????? ITEM IS NULL! WTF!!!!");
		v[NAME].setText(item.getName());
		v[USER].setText("Owner: "+item.getUserName());
		v[BUYER].setText("Buyer: "+item.getBuyerName());
		v[DESC].setText("Description: "+item.getDescription());
		v[DATE].setText("Date Added: "+item.getDate().toString());
		v[PRICE].setText("Price: "+item.getPrice());
		imageV.setImageDrawable(item.getDrawable());
		
		v[NAME].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//TODO: create dialog for each one
			}
		});
		
		v[USER].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//TODO: create dialog for each one
			}
		});
		
		v[DESC].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//TODO: create dialog for each one
			}
		});
		
		v[PRICE].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//TODO: create dialog for each one
			}
		});
		
		v[BUYER].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//TODO: create dialog for each one
			}
		});
		
		v[DATE].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//TODO: create dialog for each one
			}
		});
		
		
	}
}
