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
    
    @Override
    public int describeContents()
    {
        return 0;
    }


    

      
}
