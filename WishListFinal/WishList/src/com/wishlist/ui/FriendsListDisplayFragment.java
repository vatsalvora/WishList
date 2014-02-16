package com.wishlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FriendsListDisplayFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private View rootView;

    public static final String ARG_SECTION_NUMBER = "section_number";

    public FriendsListDisplayFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_friends_list_display,
                                         container, false);

        return rootView;
    }
}