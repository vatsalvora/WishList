package com.wishlist.obj.util;
import java.util.Comparator;

import com.wishlist.obj.WishItem;

public final class PriceComparison implements Comparator<WishItem>
{

    @Override
    public int compare(WishItem arg0, WishItem arg1)
    {
        if(arg0.getPrice().compareTo(arg1.getPrice()) == 0) return arg0.compareTo(arg1);
        return arg0.getPrice().compareTo(arg1.getPrice());
    }

}