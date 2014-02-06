package com.wishlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

	public class WishDisplayFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private View rootView;
		private TextView dummyTextView;
		
		public WishDisplayFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText("This will be the wish display fragment");
			return rootView;
		}
	}