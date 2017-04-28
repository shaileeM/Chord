package chord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.bind.DatatypeConverter;

public class FileSearch_Updated {
	/**
	 * Search file -- 80
	 * Hash and get the output 42
	 * Check 80's finger table
	 * 	42 YES
	 * 		Connect and request file
	 * 	42 NO
	 * 		Forward request to first value in 80's finger table
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 */
	public void searchingFile(String fileName, TreeMap<Integer, String> keyIPMap,
			HashMap<Integer, ArrayList<String>> fileTable, ArrayList<String> fileList) throws UnknownHostException, IOException {    {
    	
    	CreateFinger createfingerObj = new CreateFinger();
    	
    	HashMap fingerTable = createfingerObj.getHashMap();  	
    	System.out.println("**********TEST****************");
    System.out.println("dsljhklkjdskdasjlksajklsdjds"+fingerTable.get(1));    	
    	for(int i=0;i<fingerTable.size();i++){
    		System.out.println(fingerTable.get(i)+":"+i);
    	} 	
        String fileN = fileName;
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
        String file_ip,file_ip_else = null;
        int else_value;
        if(fingerTable.containsValue(value)){
        	 file_ip=keyIPMap.get(value);
        	 System.out.println("file_ip"+file_ip);
        	 System.out.println("  "+keyIPMap.get(value));
    	//	Scanner sc=new Scanner(file_ip);
            Socket s=new Socket(file_ip, 9090);
    		Scanner sc1=new Scanner(s.getInputStream());
    		PrintStream p = new PrintStream(s.getOutputStream());
    		p.println(file_ip);		
    		p.println(my_ip);
    		p.println(fileN);
    		
    		//----------------For receiving files
    		
    		 ServerSocket s1 = new ServerSocket(8888);
            
           Socket connected = s1.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connected.getInputStream()));
            File file = new File(fileN);

            if(!file.exists())
                System.out.println("File not created");

            FileWriter fw = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fw);
            String line = "";
            do
            {
                line = reader.readLine();
                writer.write(line);
            }while(line != null);

           // fileList.add(fileName);
            //fileTable.put(50, fileList);
            //System.out.println("Node 50 has file: "+fileTable.get(50));
    		
    		//--------------For receiving files
    		
    		//p.println();
    	String	temp=sc1.next();
    		System.out.println("temp value"+temp);

        }
        else{
      //  else_value=fingerTable.get(1);
        	Integer ip_next_node=keyIPMap.ceilingKey(value);
            Socket s=new Socket(ip_next_node.toString(), 9999);
    		Scanner sc1=new Scanner(s.getInputStream());
    		PrintStream p = new PrintStream(s.getOutputStream());
    		p.println(file_ip_else);		
        }
       System.out.println(fingerTable.get(1));
        	file_ip=keyIPMap.get(value);
        }
        
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

