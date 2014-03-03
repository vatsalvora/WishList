/* Author: Joon Kim
 * DialogFragment to create new WishItem for the current app user.
 * 
 * Needs more work and thought
 */

package com.wishlist.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class WishCreatorDialog extends DialogFragment{
	
	public interface WishCreatorDialogListener{
		void onDialogPositiveClick(DialogFragment dialog);
		void onDialogNegativeClick(DialogFragment dialog);
	}
	
	WishCreatorDialogListener l;
	
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try{
			l = (WishCreatorDialogListener) activity;
		}
		catch(ClassCastException e){
			throw new ClassCastException(activity.toString() + "must implement WishCreatorDialogListener");
		}
	}
	
	public Dialog onCreateDialog(Bundle saveInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		builder.setView(inflater.inflate(R.layout.dialog_create_wish, null))
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) 
			{
				// TODO Auto-generated method stub
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) 
			{
				WishCreatorDialog.this.getDialog().cancel();
			}
		});
		
		return builder.create();
	}
}
