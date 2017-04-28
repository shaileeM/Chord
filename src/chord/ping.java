package chord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Map;

public class ping
{
    public void checkHealth()
    {
        try
        {
        	String ipAddress;
        	int nodeVal;
        	for(Map.Entry<Integer,String> entry : MainClass.keyIPMap.entrySet()) {
        		nodeVal = entry.getKey();
        		   ipAddress = entry.getValue();
                   InetAddress inet = InetAddress.getByName(ipAddress);
                   System.out.println("Sending Ping Request to " + ipAddress);
                   System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
                   if(!inet.isReachable(5000)){
                	   MainClass.keyIPMap.remove(nodeVal);
                   }
        	}
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
/*
public class ping {

    public static void runSystemCommand(String command) {

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String s = "";
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String ip = "172.20.87.129"; //Any IP Address on your network / Web
        runSystemCommand("ping " + ip);
    }
}*/
