package com.wishlist.ui;

import java.util.ArrayList;

import com.wishlist.obj.WishItem;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.*;

public class ItemView extends Activity {

	private TextView nameView;
	private TextView descriptionView;
	private ListView commentsView;
	private ImageView image;
	
	private ArrayList<String> comments;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_view);
		
		nameView = (TextView) findViewById(R.id.nameOfItem);
		descriptionView = (TextView) findViewById(R.id.description);
		commentsView = (ListView) findViewById(R.id.listOfComments);
		image = (ImageView) findViewById(R.id.ItemImage);
		
		comments = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        commentsView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
		return true;
	}

}
