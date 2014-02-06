package com.wishlist.obj;

import java.util.Comparator;

public class PriceComparison implements Comparator<WishItem>{

	@Override
	public int compare(WishItem arg0, WishItem arg1) {
		if(arg0.price.compareTo(arg1.price) == 0) return arg0.compareTo(arg1);
		return arg0.price.compareTo(arg1.price);
	}

}
