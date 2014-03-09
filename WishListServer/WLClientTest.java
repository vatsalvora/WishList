import java.io.IOException;
import java.util.Scanner;
public class WLClientTest
{   
    public static final int STRING = 0;
    public static final int STRING_PLAY = 1;
    public static final int FBUSER = 2;
    public static final int WISHITEM = 3;


    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        FBUser tu = new FBUser("0", "alex", false);

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

                    else if(msg.equals("user_go"))
                    {
                        com.sendCode(FBUSER);
                        com.sendObject(tu);
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
