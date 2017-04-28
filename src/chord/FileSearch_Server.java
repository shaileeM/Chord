package chord;

                      import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.bind.DatatypeConverter;

public class FileSearch_Server {
		// TODO Auto-generated method stub
	
	public void fileSearch_Server(TreeMap<Integer, String> keyIPMap, HashMap<Integer, ArrayList<String>> fileTable,
			ArrayList<String> fileList) throws IOException {
		// TODO Auto-generated method stub
		String file_ip,actual_ip,fileN;
		ServerSocket s1=new ServerSocket(9990);
		Socket ss=s1.accept();
		Scanner sc=new Scanner(ss.getInputStream());
		file_ip=sc.nextLine();
		actual_ip=sc.nextLine();
		fileN=sc.nextLine();
		//int temp;
		//temp=number*2;
		PrintStream p=new PrintStream(ss.getOutputStream());
		//p.println("The"+fileN);
	
    	CreateFinger createfingerObj = new CreateFinger();
    	
    	HashMap fingerTable = createfingerObj.getHashMap();  	
    	System.out.println("*****TEST*****");
    System.out.println("dsljhklkjdskdasjlksajklsdjds"+fingerTable.get(1));    	
    	for(int i=0;i<fingerTable.size();i++){
    		System.out.println(fingerTable.get(i)+":"+i);
    	} 	
    //    fileN = fileName;
        System.out.println("FileName Assigned "+fileN);
        String hashedfileValue = hashFunction(fileN.getBytes(),"SHA-256");
        String digits_file = "0123456789ABCDEF";
        hashedfileValue = hashedfileValue.toUpperCase();
        System.out.println("Hashed Value of file"+hashedfileValue);
        int fileValDec = 0, fileVal = 0;
        for (int i = 0; i < hashedfileValue.length(); i++)
        {
            char c = hashedfileValue.charAt(i);
            int d = digits_file.indexOf(c);
            fileValDec = 16*fileValDec + d;
        }
        int m = 7; //Determines maximum of nodes; Can be set by user
        System.out.println("1*stuff+digit value		"+fileValDec);
        int maxActiveNode = 1;
        int activeNode;
        for (int i=0; i<m; i++)
        {
            maxActiveNode *= 2;
        }
        fileVal = Math.abs(fileValDec)%maxActiveNode;
        System.out.println("in file.........");     
		InetAddress ipAddr = InetAddress.getLocalHost();
		String my_ip=ipAddr.getHostAddress();
	//System.out.println("aslksdalkdasjdlkas"+ipAddr.getHostAddress());
//	System.out.println("string_my ip"+my_ip);

	

        int value ;
        if( fileVal > keyIPMap.lastKey())
            value = keyIPMap.firstKey();
        else value = keyIPMap.ceilingKey(fileVal);
        System.out.println("Value"+value);
   //     file_ip,
        String file_ip_else = null;
        int else_value;
        if(fingerTable.containsValue(value)){
        	 file_ip=keyIPMap.get(value);
        	 System.out.println("file_ip"+file_ip);
        	System.out.println("  "+keyIPMap.get(value));
    	//	Scanner sc=new Scanner(file_ip);
            Socket s=new Socket(file_ip, 9090);
    		Scanner sc1=new Scanner(s.getInputStream());
    		PrintStream p1 = new PrintStream(s.getOutputStream());
    		p1.println(file_ip);		
    		p1.println(my_ip);
    		p1.println(fileN);
    		//p.println();
    	String	temp=sc1.next();
    		System.out.println("temp value"+temp);
    		Socket cl = new Socket(my_ip, 8888);
    		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
            int i=0;
            writer.write(fileN);
            writer.newLine();
            writer.flush();

            //Writing contents of file
            /*FileReader fileInput = new FileReader(fileN);
            BufferedReader br = new BufferedReader(fileInput);
            String line = "";
            do
            {
                line = br.readLine();
                writer.write(line);
            }while (br.readLine() != null);

            cl.close();*/
            Socket client=new Socket("172.20.99.19", 8097);
            System.out.println("Connected to server " + client.getInetAddress()
                    + ": " + client.getPort());
            System.out.println("connected");
            
            
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            fileN="/Users/ramnakri/Documents/chord 2/src/mini.jpg";
            FileInputStream fis = new FileInputStream(fileN);
            byte[] buffer = new byte[4096];
            while(fis.read(buffer)>0)
            {
            	System.out.println("Sending");
            	dos.write(buffer);
            }
            fis.close();
            dos.close();
    		
    		  }
        else{
      //  else_value=fingerTable.get(1);
        	Integer ip_next_node=keyIPMap.ceilingKey(value);
            Socket s=new Socket(ip_next_node.toString(), 9999);
    		Scanner sc1=new Scanner(s.getInputStream());
    		PrintStream p1 = new PrintStream(s.getOutputStream());
    		p1.println(file_ip_else);		
        }
       System.out.println(fingerTable.get(1));
        	file_ip=keyIPMap.get(value);
        }
        
			
    
	
	
    public static String hashFunction(byte[] inputBytes, String algorithm)
    {
        String hashValue="";
        try
        {
            MessageDigest digest= MessageDigest.getInstance(algorithm);
            digest.update(inputBytes);
            byte[] digestedByte=digest.digest();
            hashValue= DatatypeConverter.printHexBinary(digestedByte).toLowerCase();
        }
        catch(Exception e)
        {
            System.out.println("Error in hashing function");
        }
        return hashValue;
    }	
 
	}