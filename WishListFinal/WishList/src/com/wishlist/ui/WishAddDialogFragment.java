package com.wishlist.ui;

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
		
		final View root = inflater.inflate(R.layout.dialog_add_wish, null);
		final EditText name_edit = (EditText) root.findViewById(R.id.edit_name);
		final EditText price_edit = (EditText) root.findViewById(R.id.edit_price);
		final EditText description_edit = (EditText) root.findViewById(R.id.edit_description);
		
		builder.setView(root)
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) 
			{
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
