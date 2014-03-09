import java.io.IOException;
import java.util.Scanner;
public class WLClientTest
{
    public static void main(String[] args)
    {
        try
        {
            WLServerCom com = new WLServerCom();
            com.sendCMD("play");
            Scanner sc = new Scanner(System.in);
            int x = sc.nextInt();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        
    }
}
