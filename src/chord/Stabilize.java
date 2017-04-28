package chord;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Stabilize extends Thread {

	
	public void run(){
		if(MainClass.myIp==MainClass.leader)
		{System.out.println("Leader Stabilizing");
	    try{
		
	
	    
	    
	    // ping method to check the health of all nodes and update the tree map accordingly 
	  
        	String ipAddress;
        	int nodeVal;
        	for(Map.Entry<Integer,String> entry : MainClass.keyIPMap.entrySet()) {
        		   nodeVal = entry.getKey();
        		   ipAddress = entry.getValue();
                   InetAddress inet = InetAddress.getByName(ipAddress);
                   System.out.println("Sending Ping Request to " + ipAddress);
                   System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
                   if(!inet.isReachable(5000)){
                	  System.out.println(inet +"Host is NOT reachable deleting " );
                	   MainClass.keyIPMap.remove(nodeVal);
                   }
        	}
	    
        	   ServerSocket s =  new ServerSocket(9093); // socket port 9093 for deleting a node
    		   System.out.println("socket port 9093 for deleting a node");
    		   Socket connected = s.accept();
    		   ObjectInputStream reader=new ObjectInputStream(connected.getInputStream());  
    		   Integer  node =(Integer)reader.readObject();
    		   System.out.println(" deleteing node : " + node);
                   MainClass.keyIPMap.remove(node);
           
	    
	    //send treemap to all the nodes
	    for(Map.Entry<Integer,String> entry : MainClass.keyIPMap.entrySet()) {
 		ipAddress = entry.getValue();
                InetAddress inet = InetAddress.getByName(ipAddress);
        	Socket client=new Socket(inet, 9095);
	        ObjectOutputStream TreeoutputStream = null;
	        TreeoutputStream = new ObjectOutputStream(client.getOutputStream());
	        TreeoutputStream.writeObject(MainClass.keyIPMap);
	        System.out.println("sending  treemap to :" + inet);
	     }
	    }
		
		catch(Exception E){
			
		} 
		
	}
}
}
