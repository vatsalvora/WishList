/* Author: Joon Kim
 *
 * subclass of User in our system who uses Facebook
 *
 * Edited by: Alex Bryan
 * EDIT: implements Parcelable here, instead of User superclass
 */


public class FBUser extends User
{

	public FBUser(){
		
	}
	
    public FBUser(String uid, String name, boolean isAppUser)
    {
        super(uid, name, isAppUser);
    }
    
    public int describeContents()
    {
        return 0;
    }

    public String toString()
    {
        /* Here for debug purposes */
        String res = uid + "\t" + name + "\t" + isAppUser;
        return res;
    }


    

      
}
