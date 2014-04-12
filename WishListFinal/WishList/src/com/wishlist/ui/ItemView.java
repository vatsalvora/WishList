package com.wishlist.ui;

import com.wishlist.obj.WishItem;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.*;

/*
 * The item detail view for wishes
 * 
 */


public class ItemView extends FragmentActivity implements WishEditDialogFragment.WishEditDialogListener
{
	public static final int NAME = 0;
	public static final int USER = 1;
	public static final int BUYER = 2;
	public static final int DATE = 3;
	public static final int DESC = 4;
	public static final int PRICE = 5;
	public static final int TOTAL = 6;
	
	private WishItem item;
	private boolean isAppUser;
	private int hashCode;
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
        initData();
		display();
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
    
    protected void initData(){
    	item = (WishItem) Transporter.unpackObject(this.getIntent().getExtras(), Transporter.WISH);
    	isAppUser = Transporter.unpackBoolean(this.getIntent().getExtras(), Transporter.USER);
		
    	if(isAppUser){
			for(int i=0; i<TOTAL; i++){
				final int send = i; //ugh, so retarded...
				if(i == NAME || i == DESC || i == PRICE){
					v[i].setOnLongClickListener(new OnLongClickListener(){
						public boolean onLongClick(View j){
							hashCode = send;
							showUpdateDialog(send);
							return true;
						}
					});
				}
			}
    	}
		
	}
    
    protected void display(){
    	v[NAME].setText(item.getName());
		v[USER].setText("Owner: "+item.getUserName());
		v[BUYER].setText("Buyer: "+item.getBuyerName());
		v[DESC].setText("Description: "+item.getDescription());
		v[DATE].setText("Date Added: "+item.getDate().toString());
		v[PRICE].setText("Price: "+item.getPrice());
		//imageV.setImageDrawable(item.getDrawable());
    }
    
    protected void update(int code){
    	switch(code){
    		case NAME:
    			v[NAME].setText(item.getName());
    			break;
    		case USER:
    			v[USER].setText("Owner: "+item.getUserName());
    			break;
    		case BUYER:
    			v[BUYER].setText("Has Been Claimed: "+(item.getStatus()==1 ? "Yes": "No"));
    			break;
    		case DESC:
    			v[DESC].setText("Description: "+item.getDescription());
    			break;
    		case DATE:
    			v[DATE].setText("Date Added: "+item.getDate().toString());
    			break;
    		case PRICE:
    			v[PRICE].setText("Price: "+item.getPrice());
    			break;
    		default:
    			break;
    	}
    }
    
    protected void showUpdateDialog(int send){
    	DialogFragment d = new WishEditDialogFragment();
    	d.show(getSupportFragmentManager(), "WishUpdateFragment");
    }
    
    public WishItem getItem(){
    	return item;
    }
    
    public int getHashCode(){
    	return hashCode;
    }
    
	@Override
	public void onDialogPositiveClick(WishEditDialogFragment dialog) {
		update(hashCode);
	}

	@Override
	public void onDialogNegativeClick(WishEditDialogFragment dialog) {
	}

}
