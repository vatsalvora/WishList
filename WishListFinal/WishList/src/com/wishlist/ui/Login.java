package com.wishlist.ui;

import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;
import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;
import com.wishlist.serv.WLServerCom;

import android.util.Log;
import android.content.Intent;
import android.graphics.Bitmap;

public final class Login extends Activity
{

    private User currentAppUser= null; //user object for the Facebook user actually using the app.
    private ArrayList<User> friends = null; //ID of friends
    private Bundle FBData = null;
    private Session s = null; //current FB Session object

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startFBSession();
        //test();
    }

    protected void onResume()
    {
    	super.onResume();
    }
    
    protected void onStart()
    {
    	super.onStart();
    }
    
    protected void onPause()
    {
    	super.onPause();
    }
    
    protected void onRestart()
    {
    	super.onRestart();
    }
    
    protected void onStop()
    {
    	super.onStop();
    }
    
    protected void onDestroy()
    {
    	super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //start the main activity
    	super.onActivityResult(requestCode, resultCode, data);

        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }
    
    protected void pack()
    {
    	FBData = new Bundle();
    	Transporter.pack(FBData, Transporter.USER, currentAppUser);
    	Transporter.pack(FBData, Transporter.FRIENDS, friends);
    }
    
    protected void start()
    {
    	 Intent intent = new Intent(this, WishListMain.class);
         intent.putExtras(FBData);
         startActivity(intent);
    }
    
    protected void startFBSession()
    {
    	 // start Facebook Login
        s = Session.openActiveSession(this, true, new Session.StatusCallback()
        {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception)
            {
                if (session.isOpened())
                {
                	Request.newMeRequest(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                          if (user != null) {Log.e("Order","2");
                            //Log.i("Facebook",user.getName());
                            //Log.i("Facebook", user.getId());
                            currentAppUser = new User(user.getId(),user.getName(), true);
                          }
                        }
                      }).executeAsync();
                	Request.newMyFriendsRequest(session, new Request.GraphUserListCallback()
                    {	

                        @SuppressWarnings("deprecation")
						public void onCompleted(List<GraphUser> users, Response response)
                        {Log.e("Order","2");
                        	friends = new ArrayList<User>();
                        	ArrayList<String> ids = new ArrayList<String>();
                            for(GraphUser i : users){ 
                            	//Log.i("Facebook", i.getName());
                            	//Log.i("Facebook", i.getId());
                            	friends.add(new User(i.getId(),i.getName(),false));
                            	ids.add(i.getId());
                            }
                            Collections.sort(ids);
                            /*
                            RegisteredUser dbList = new RegisteredUser();
                            try {
                            	dbList.execute(ids);
								ArrayList<String> dbID = dbList.get();
								ArrayList<User> regList = new ArrayList<User>();
								int u=0;
								int f=0;
								while(f<friends.size() && u<dbID.size()){
									if(friends.get(f).getUID() != dbID.get(u))
									{
										f++;
									}
									else{
										regList.add(friends.get(f));
										u++;
										f++;
									}
								}
								friends = regList;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							*/
                            Collections.sort(friends);
                        	
                            WishRetrieval wishRet = new WishRetrieval();
                        	wishRet.execute(currentAppUser.getUID(),currentAppUser.getName());
                        	ArrayList<WishItem> wishes = new ArrayList<WishItem>();
                        	/*
                        	try{
                        		wishes = wishRet.get();
                        	}
                        	catch(Exception e){
                        		
                        	}
                        	*/
                        	currentAppUser.setList(wishes);
                        	//Log.i("IsNULL",friends.toString());
                            pack();
                            start();
                           
                        }
                    }).executeAsync();
                }
                else if(session.isClosed()){
                	Log.i("Facebook", session.getState().toString());
                }
            }
        });
    }
    
    protected void stopFBSession()
    {
    	s.close();
    }
    
    @SuppressWarnings("deprecation")
	protected void test()
    {// sets a dummy user for testing purposes
    	currentAppUser = new User("0", "John Doe", true);
    	friends = new ArrayList<User>();
    	for(int i=1; i<11; i++){
    		friends.add(new User(Integer.toString(i), Integer.toString(i), false));
    	}

    	ArrayList<WishItem> wishes = new ArrayList<WishItem>();

    	wishes.add(new WishItem("dummyID1", "Red Ryder BB Gun", currentAppUser.getUID(), currentAppUser.getName(), "", "", "The Red Ryder BB Gun is a lever-action, spring piston air gun with a smooth bore barrel, adjustable iron sights, and a gravity feed magazine with a 650 BB capacity", "10", 0, new Date(3,4,2014)));
    	wishes.add(new WishItem("dummyID2", "A Feast of Ice and Fire", currentAppUser.getUID(), currentAppUser.getName(), "", "", "Fresh out of the series that redefined fantasy, comes the cookbook that may just redefine dinner . . . and lunch, and breakfast. ", "20", 0, new Date(3,4,2014)));
    	
    	currentAppUser.setList(wishes);
    	
    	pack();
    	start();
    }
}
class RegisteredUser extends AsyncTask<ArrayList<String>, Integer, ArrayList<String>> {
	@Override
	protected ArrayList<String> doInBackground(ArrayList<String>... params) {
		
        ArrayList<String> a = params[0];
        return a;
	}

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<String> result) {
        Log.d("Image",result.toString());
    }
}
class WishRetrieval extends AsyncTask<String, Integer, ArrayList<WishItem>> {
	@Override
	protected ArrayList<WishItem> doInBackground(String... params) {
		
        String uid = params[0];
        String uname = params[1];
        ArrayList<WishItem> wish = new ArrayList<WishItem>();
		try {
			wish = WLServerCom.listWishes(uid, uname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return wish;
	}

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<String> result) {
        Log.d("Image",result.toString());
    }
}