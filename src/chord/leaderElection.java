package chord;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.TreeMap;

class Leader extends Thread{
	 TreeMap<Integer, String> map ;
	 	 public Leader(TreeMap<Integer, String> map){
		 this.map = map;
		 
	}
        public  void run() {
    	try{
    
         
        ServerSocket s =  new ServerSocket(9091);
        Scanner ipScan = new Scanner(System.in);
        System.out.println("Enter your own Ip Address ");
         MainClass.myIp = ipScan.nextLine();
        
        System.out.println("Server waiting for client on port " + s.getLocalPort());
        int count = 0;
        do{
            count = count + 1;
           Socket connected = s.accept();
        
            if(!MainClass.keyIPMap.isEmpty() && MainClass.myIp.equals(MainClass.keyIPMap.lastEntry().getValue())){
            	 System.out.println("the leader is : " + map.lastEntry().getValue());  
            	 new leaderElection(connected, count, map).start();
           }
        }
        while(true);        
    }
    catch(Exception E){
    	
    }
}
}

public class leaderElection extends Thread{
    /**
     * Get current node value
     * compare with the tree map
     * If it is the last value, set leader=1
     * @throws IOException 
     *
     */
   // public void getConnection() throws IOException{
	 Socket connected = null;
	 int mycount;
	 TreeMap<Integer, String> map ;
	 public leaderElection(Socket clientSocket, int count, TreeMap<Integer, String> map ) {
	       connected = clientSocket;
	        mycount = count;
	        this.map = map;
	    }

	public void run()  {
		
		System.out.println("in leader Election thread");
        ServerSocket s;
		try {

        System.out.println("New connection accepted " + connected.getInetAddress()
                + ": " + connected.getPort());
        DataInputStream reader=new DataInputStream(connected.getInputStream());  
        System.out.println("after reader");
        String  ipNodeTobeAdded=(String)reader.readUTF(); 

        System.out.println("after readline");
        System.out.println("ip of node to be added : " + ipNodeTobeAdded);
        ClassAdd c = new ClassAdd();
        int nodeVal = c.addNode(ipNodeTobeAdded, map);
        System.out.println("nodeval : "+nodeVal);
        //send treeMap to all nodes
         ObjectInputStream inputStream = null;
         ObjectOutputStream TreeoutputStream = null;
        TreeoutputStream = new ObjectOutputStream(connected.getOutputStream());
        TreeoutputStream.writeObject(map);
        ObjectOutputStream nodeOutputStream = new ObjectOutputStream(connected.getOutputStream());
        nodeOutputStream.writeObject(nodeVal);
        connected.close();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
