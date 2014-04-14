package com.wishlist.obj.util;

import java.util.Comparator;

import com.wishlist.obj.User;

public class IDComparison  implements Comparator<User>{

    @Override
    public int compare(User arg0, User arg1)
    {
        if(arg0.getUID().compareTo(arg1.getUID()) == 0) return arg0.compareTo(arg1);
        return arg0.getUID().compareTo(arg1.getUID());
    }

}
