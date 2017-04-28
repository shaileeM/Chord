package chord;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.TreeMap;

public class ClientAdd  extends Thread{
	Socket client = null;
    int mycount;
    String hostname;
    
    public ClientAdd(Socket clientSocket, String hn, int count) {
	       client = clientSocket;
	       hostname = hn;
	       mycount = count;
	    }
    
	public void run()  {
		
				try {
					// Socket client=new Socket("localHost", 9090);
				     DataOutputStream dout=new DataOutputStream(client.getOutputStream());  
				     dout.writeUTF(hostname);  
				     ObjectInputStream  inStream = new ObjectInputStream(client.getInputStream());
                                     TreeMap<Integer, String> map = (TreeMap<Integer, String>) inStream.readObject();
				     MainClass.keyIPMap = map;
				     System.out.println("map" + map.lastEntry().getValue());
				     ObjectInputStream reader=new ObjectInputStream(client.getInputStream());  
				     MainClass.nodeVal =(Integer)reader.readObject();
				     System.out.println("Node Value is : " +    MainClass.nodeVal);

				     CreateFinger obj1 = new CreateFinger();
				     obj1.fingerTable(   MainClass.nodeVal, map);
				     new Leader(MainClass.keyIPMap).start();
				     if(MainClass.myIp!=MainClass.leader) new Node().start();
				     if(MainClass.myIp==MainClass.leader) new Stabilize().start();
				     client.close();
				     System.out.println("connection closed! ");
					}
                                catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
}
