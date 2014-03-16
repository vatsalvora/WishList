import java.io.IOException;
import java.util.Scanner;
public class WLClientTest
{   
    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int USER_ADD = 2;
    public static final int WISH_ADD = 3;
    public static final int USER_SEND = 4;
    public static final int WISH_RM = 5;
    public static final int WISH_UP = 6;

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        FBUser tu = new FBUser("0", "alex", false);
        WishItem wi = new WishItem("w-000", "Coke", "u-000", "Alex");

        try
        {
            boolean done = false;
            String msg = "";
            WLServerCom com = new WLServerCom();

            while(true)
            {
                try
                {
                    msg = sc.next();

                    if(msg.equals("bye"))
                    {
                        break;
                    }

                    else if(msg.equals("special"))
                    {
                        com.sendCode(STRING_PLAY);
                        com.sendObject("play");
                    }

                    else if(msg.equals("user_add"))
                    {
                        com.addUser(tu);
                    }

                    else if(msg.equals("wish_add"))
                    {
                        com.addWish(wi); 
                    }
                    else if(msg.equals("user_send"))
                    {
                        com.sendCode(USER_SEND);
                        try
                        {
                            FBUser fb = (FBUser)com.getObject();
                            System.out.println(fb.toString());
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if(msg.equals("wish_rm"))
                    {
                        com.rmWish("w-000");
                    }
                    else if(msg.equals("wish_update"))
                    {
                        wi.setDescription("Delicious");
                        com.updateWish(wi);
                    } 
                        
                    else
                    {
                        com.sendCode(STRING);
                        com.sendObject(msg);
                    }
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                    break;
                }
            }

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        
    }
}
