package com.wishlist.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class _wishlist_view extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__wishlist_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu._wishlist_view, menu);
		return true;
	}

}
