package com.wishlist.obj.util;

import java.util.Comparator;

import com.wishlist.obj.WishItem;

public final class DateComparison implements Comparator<WishItem>
{

    @Override
    public int compare(WishItem arg0, WishItem arg1)
    {
        if(arg0.getDate().compareTo(arg1.getDate()) == 0) return arg0.compareTo(arg1);
        return arg0.getDate().compareTo(arg1.getDate());
    }

}