package com.wishlist.ui;

import com.wishlist.obj.WishItem;
import com.wishlist.ui.WishUpdateDialogFragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.*;

/*
 * The item detail view for wishes
 * 
 */


public class ItemView extends FragmentActivity implements WishUpdateDialogFragment.WishUpdateDialogListener
{
	private WishItem item;
	private int isAppUser;
	
	public static final int NAME = 0;
	public static final int USER = 1;
	public static final int BUYER = 2;
	public static final int DATE = 3;
	public static final int DESC = 4;
	public static final int PRICE = 5;
	public static final int TOTAL = 6;
	
	private TextView v[] = new TextView[TOTAL];
	
    //private ListView commentsV;
    //private ImageView imageV;
    
    //private ArrayList<String> comments;
    //private ArrayAdapter<String> adapter;
       
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
		
		//comments = new ArrayList<String>();
		//adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
		//commentsV.setAdapter(adapter);
        
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
    
    protected void displayItem(){
    	item = (WishItem) Transporter.unpackObject(this.getIntent().getExtras(), Transporter.WISH);
    	isAppUser = Transporter.unpackInteger(this.getIntent().getExtras(), Transporter.USER);
		
    	v[NAME].setText(item.getName());
		v[USER].setText("Owner: "+item.getUserName());
		v[BUYER].setText("Buyer: "+item.getBuyerName());
		v[DESC].setText("Description: "+item.getDescription());
		v[DATE].setText("Date Added: "+item.getDate().toString());
		v[PRICE].setText("Price: "+item.getPrice());
		//imageV.setImageDrawable(item.getDrawable());
		
		if(isAppUser == 1){
			for(int i=0; i<TOTAL; i++){
				v[i].setOnLongClickListener(new OnLongClickListener(){
					public boolean onLongClick(View j){
						showUpdateDialog();
						return true;
					}
				});
				switch(i){
					case 0:
						v[i].setOnClickListener(new OnClickListener(){
							public void onClick(View j){
								Toast t = new Toast(getApplicationContext());
								t.setText("blahhhhhhhhhhhh");
								t.show();
							}
						});
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					default:
						break;
				}
			}
		}		
		
	}
    
    protected void showUpdateDialog(){
    	DialogFragment d = new WishUpdateDialogFragment();
    	Bundle b = new Bundle();
    	b.putParcelable(Transporter.WISH, item);
    	d.setArguments(b);
    	d.show(getSupportFragmentManager(), "WishUpdateFragment");
    }
    
	@Override
	public void onDialogPositiveClick(WishUpdateDialogFragment dialog) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}

	@Override
	public void onDialogNegativeClick(WishUpdateDialogFragment dialog) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}

}
