package com.wishlist.ui;

import java.sql.Date;

import com.wishlist.obj.User;
import com.wishlist.obj.WishItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class WishAddDialogFragment extends DialogFragment{
	
	public interface WishAddDialogListener{
		void onDialogPositiveClick(WishAddDialogFragment dialog);
		void onDialogNegativeClick(WishAddDialogFragment dialog);
	}
	
	private WishAddDialogListener l;
	
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	l = (WishAddDialogListener) getActivity();
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
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		final User u = ((WishListMain) getActivity()).getCurrentUser();		
		final View root = inflater.inflate(R.layout.dialog_add_wish, null);
		final EditText name_edit = (EditText) root.findViewById(R.id.edit_name);
		name_edit.setHint("Enter name of item here");
		
		final EditText price_edit = (EditText) root.findViewById(R.id.edit_price);
		price_edit.setHint("Enter price of item here");
		
		final EditText description_edit = (EditText) root.findViewById(R.id.edit_description);
		description_edit.setHint("Enter description here");
		
		builder.setView(root)
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) 
			{
				String name = name_edit.getText().toString();
				String price = price_edit.getText().toString();
				String description = description_edit.getText().toString();
				//TODO set the date
				
				WishItem w = new WishItem("", name, u.getUID(), u.getName());
				w.setPrice(price);
				w.setDescription(description);
				u.addItem(w);
				
				l.onDialogPositiveClick(WishAddDialogFragment.this);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) 
			{
				l.onDialogNegativeClick(WishAddDialogFragment.this);
			}
		});
		
		return builder.create();
		
	}
}
