/* Author: Joon Kim
 *
 * compares WishItems by date
 */


import java.util.Comparator;

public class DateComparison implements Comparator<WishItem>
{

    @Override
    public int compare(WishItem arg0, WishItem arg1)
    {
        if(arg0.dateAdded.compareTo(arg1.dateAdded) == 0) return arg0.compareTo(arg1);
        return arg0.dateAdded.compareTo(arg1.dateAdded);
    }

}
