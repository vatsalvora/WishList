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
import android.widget.TextView;

public class WishEditDialogFragment extends DialogFragment{
	
	public interface WishEditDialogListener{
		void onDialogPositiveClick(WishEditDialogFragment dialog);
		void onDialogNegativeClick(WishEditDialogFragment dialog);
	}
	
	private WishEditDialogListener l;
	
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	l = (WishEditDialogListener) getActivity();
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
		
		final WishItem w = ((ItemView) getActivity()).getItem();
		final int hash = ((ItemView) getActivity()).getHashCode();
		
		final View root = inflater.inflate(R.layout.dialog_update_wish, null);
		final TextView tv = (TextView) root.findViewById(R.id.update_wish_title);
		tv.setText("Update "+w.getName());
		final EditText ev = (EditText) root.findViewById(R.id.edit_field);
		ev.setHint("Enter update info here");
		
		builder.setView(root)
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) 
			{
				String newinfo = ev.getText().toString();
				switch(hash){
					case ItemView.NAME:
						w.setWishName(newinfo);
						break;
					case ItemView.DESC:
						w.setDescription(newinfo);
						break;
					case ItemView.PRICE:
						w.setPrice(newinfo);
						break;
					default:
						break;
				}
				l.onDialogPositiveClick(WishEditDialogFragment.this);
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) 
			{
				l.onDialogNegativeClick(WishEditDialogFragment.this);
			}
		});
		
		return builder.create();
		
	}
}
