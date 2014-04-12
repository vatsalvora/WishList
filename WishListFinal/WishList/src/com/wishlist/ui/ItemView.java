package com.wishlist.ui;

import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;
import com.wishlist.serv.WLServerCom;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
	private User appUser;
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
        
        v[NAME] = (TextView) findViewById(R.id.itemName);
		v[DESC] = (TextView) findViewById(R.id.description);
		v[BUYER] = (TextView) findViewById(R.id.itemClaimed);
		v[PRICE] = (TextView) findViewById(R.id.price);
		
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
    	appUser = (User) Transporter.unpackObject(this.getIntent().getExtras(), Transporter.APPUSER);
		isAppUser = Transporter.unpackBoolean(this.getIntent().getExtras(), Transporter.USER);
    	
    	if(isAppUser){
			for(int i=0; i<TOTAL; i++){
				if(i == NAME || i == DESC || i == PRICE){
					v[i].setOnLongClickListener(new OnLongClickListener(){
						public boolean onLongClick(View j){
							showUpdateDialog();
							return true;
						}
					});
				}
			}
    	}
		
	}
    
    protected void display(){
    	v[NAME].setText(item.getName());
		v[BUYER].setText((item.getStatus()==1 ? "Item is claimed": "Item is not claimed"));
		v[DESC].setText(item.getDescription());
		v[PRICE].setText("$"+item.getPrice());
		v[BUYER].setOnLongClickListener(new OnLongClickListener(){
			public boolean onLongClick(View j){						
				WishItem item = ItemView.this.item; 
				int status = item.getStatus();
				status = 1-status;
				item.setStatus(status);
				if(status == 0) {
					item.setBuyer("", "dummy");
					Log.e("UnClaim","UnClaim");
				}
				else{
					item.setBuyer(appUser.getUID(), appUser.getName());
					Log.e("Claim","Claim");
				}
				final WishItem w = item;
				Toast.makeText(ItemView.this, (status==0)?"Item Unclaimed!":"Item Claimed!" , Toast.LENGTH_LONG).show();
				new Thread(){
					public void run(){
						try {
							Log.e("Status", w.getStatus()+"");
							Log.e("Buyer", w.getBID());
							WLServerCom.updateWish(w);
						} catch (Exception e) {
							Log.e("Update",e.toString());
						} 
					}
				}.start();
				ItemView.this.update(ItemView.BUYER);
				return true;
			}
		});	
    }
    
    protected void update(int code){
    	switch(code){
    		case NAME:
    			v[NAME].setText(item.getName());
    			break;
    		case BUYER:
    			v[BUYER].setText((item.getStatus()==1 ? "Item is claimed": "Item is not claimed"));
    			break;
    		case DESC:
    			v[DESC].setText(item.getDescription());
    			break;
    		case PRICE:
    			v[PRICE].setText("$"+item.getPrice());
    			break;
    		default:
    			break;
    	}
    }
    
    protected void showUpdateDialog(){
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
