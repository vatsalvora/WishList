import java.io.IOException;
import java.util.Scanner;
public class WLClientTest
{   
    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int USER_ADD = 2;
    public static final int WISHITEM = 3;
    public static final int USER_SEND = 4;


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
                        com.sendCode(USER_ADD);
                        com.sendObject(tu);
                    }

                    else if(msg.equals("wish_go"))
                    {
                        com.sendCode(WISHITEM);
                        com.sendObject(wi);
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
