package com.wishlist.ui;

import com.wishlist.obj.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class WishDeleteDialogFragment extends DialogFragment{
	
	public interface WishDeleteDialogListener{
		void onDialogPositiveClick(WishDeleteDialogFragment dialog);
		void onDialogNegativeClick(WishDeleteDialogFragment dialog);
	}
	
	private WishDeleteDialogListener l;
	int position;
	
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	l = (WishDeleteDialogListener) getActivity();
    	position =this.getArguments().getInt(Transporter.WISH);
    }
    
    public void onStart()
    {
    	super.onStart();
    }
    
    public void onResume()
    {
    	super.onResume();
    }
    
    public void onStop()
    {
    	super.onStop();
    }
    
    public void onDestroy()
    {
    	super.onDestroy();
    }
    
    public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		final User u = ((WishListMain) getActivity()).getCurrentUser();
		
		builder.setMessage("Really delete this item? It will be gone forever.")
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) 
			{
				u.removeItem(position);
				l.onDialogPositiveClick(WishDeleteDialogFragment.this);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) 
			{
				l.onDialogNegativeClick(WishDeleteDialogFragment.this);
			}
		});
		
		return builder.create();
	}
}
