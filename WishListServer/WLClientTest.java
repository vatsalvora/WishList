import java.io.IOException;
import java.util.Scanner;
public class WLClientTest
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

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
                    com.sendCMD(msg);

                    if(msg.equals("bye"))
                    {
                        break;
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
