/* Author: Joon Kim
 *
 * Pair class to link two objects together
 * 
 * EDIT: implementing Parcelable with generics is extremely tricky. Solution here is unfavorable. (Joon)
 * EDIT2: Fuck it. Went into StringPair
 *
 * Note by Alex:
 * I love the cursing. Let the hate flow through you
 *
 */
import java.io.Serializable;


/*
 * Note on Serializable:
 *
 * While a StringPair is never sent over a network on it's own,
 * a StringPair object is sent over the network as part of a
 * WishItem object, so making StringPair Serializable is necessary
 */
public class StringPair implements Serializable
{
    public String first;
    public String second;

    public StringPair()
    {
        throw new RuntimeException("Invalid constructor!");
    }

    public StringPair(String a, String b)
    {
        first = a;
        second = b;
    }

    public int describeContents()
    {
        return 0;
    } 

    

}
