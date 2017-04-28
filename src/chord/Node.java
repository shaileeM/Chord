package chord;

import chord.MainClass;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

public class Node extends Thread{

	// this will keep listening to the leader on socket on 9095
	// only if NOT leader then this will run
	//get the updated Treemap and update its finger Table
	
	
	public void run(){
		if(MainClass.myIp!=MainClass.leader)
		{
	try{
		
	  ServerSocket s =  new ServerSocket(9095);
	  Socket connected = s.accept();
	  ObjectInputStream  inStream = new ObjectInputStream(connected.getInputStream());
	  System.out.println("at instream");
	  TreeMap<Integer, String> map = (TreeMap<Integer, String>) inStream.readObject();
	  MainClass.keyIPMap = map;
	  CreateFinger obj1 = new CreateFinger();
	  obj1.fingerTable(MainClass.nodeVal, map);
	  
	  
	}
		
	catch(Exception E){
		
	}
	
	}
	}
}
