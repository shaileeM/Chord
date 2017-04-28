package chord;

import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Search_Receive
{
    public static void filesearch_Receive()    
    {
      
        try
        {
        	//To receive search result
            HashMap<Integer, ArrayList<String>> fileTable = new HashMap<Integer, ArrayList<String>>();

            ArrayList<String> fileList = new ArrayList<>();

            ServerSocket s = new ServerSocket(8097);
            System.out.println("Server waiting for client on port for file "+s.getLocalPort());
            Socket connected = s.accept();

            System.out.println("New connection accepted " + connected.getInetAddress()
                    + ": " + connected.getPort());

            DataInputStream dis = new DataInputStream(connected.getInputStream());
            FileOutputStream fos = new FileOutputStream("testfile1.jpg");
            byte[] buffer = new byte[4096];
           
            int fileSize = 151238;
            int read =0;
            int totalRead = 0;
            int remaining = fileSize;
            while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0)
            {
            	totalRead +=read;
            	remaining -= read;
            	System.out.print("Read: "+totalRead+" bytes");
            	fos.write(buffer, 0, read);
            }
            fos.close();
            dis.close();
          
        }
        catch(IOException e)
        {
        	
        }
        
    }
}