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
	
	private WishItem item;
	private TextView tv;
	private EditText ev;
	private View root;
	
	public interface WishUpdateDialogListener{
		void onDialogPositiveClick(WishEditDialogFragment dialog);
		void onDialogNegativeClick(WishEditDialogFragment dialog);
	}
	
	public void onCreate(Bundle savedInstanceState)
    {	
    	super.onCreate(savedInstanceState);
    	item = (WishItem) Transporter.unpackObject(this.getArguments(), Transporter.WISH);
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
		
		root = inflater.inflate(R.layout.dialog_update_wish, null);
		tv = (TextView) root.findViewById(R.id.update_wish_title);
		tv.setText("Update "+item.getName());
		ev = (EditText) root.findViewById(R.id.editfield);
		ev.setHint("Enter update info here");
		
		builder.setView(root)
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) 
			{
				//TODO
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) 
			{
				
			}
		});
		
		return builder.create();
		
	}
}
