package chord;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javax.xml.bind.DatatypeConverter;


public class AddFile
{
    public void addingFile(String fileName,TreeMap<Integer, String> keyIPMap, HashMap<Integer, ArrayList<String>> fileTable,ArrayList<String> fileList)
    {
        String fileN = fileName;
        System.out.println("FileName Assigned "+fileN);


        //File is stored at the first node whose value is greater than or equal to the key value
        //To find to which node the file needs to go check the global array list to check if there is a node with a same value or next greatest value
        //Having gotten the value check weather the current node has a connection path to the node
        // If it is there(N25)s send the file directly else send to the current node's successor(N32) to continue the process and finally send the file to the correct
        //Append the file name to the finger table entry
        //WHILE CALLING FINGER TABLE CLASS PASS ARRAY LIST
        // Refer to the finger table of the node for sending the file
        // Send the file to the node on the basis of finger table
        // Send using socket
        //Check if file is duplicate
        //

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

        //Calculating 2 power m.
        for (int i=0; i<m; i++)
        {
            maxActiveNode *= 2;
        }
        //Truncated hashed value within range of 2^m

        fileVal = Math.abs(fileValDec)%maxActiveNode;
        //    keyIPmap.put(nodeVal, host);
        //Checking for finger table value correctness DELETE LATER
        //     keyIPmap.put(100, "dajkhskj");
        //   keyIPmap.put(90, "adkljsa");

        //   CreateFinger obj = new CreateFinger();
        //     obj.fingerTable(nodeVal, host, keyIPmap);
        System.out.println("in file.........");
        //	System.out.println("NodeVal"+nodeVal);
//System.out.println(fileVal);
//FileVal is the received hash value
//Now need to check if it is greater than or equal to
        int value ;

        System.out.println("fileVal"+fileVal);
        System.out.println("keyIPMap.lastKey()"+keyIPMap.lastKey());
        System.out.println("keyIPMap.firstKey()"+keyIPMap.firstKey());
        System.out.println("keyIPMap.ceilingKey(fileVal)"+keyIPMap.ceilingKey(fileVal));

        if( fileVal > keyIPMap.lastKey())
            value = keyIPMap.firstKey();
        else value = keyIPMap.ceilingKey(fileVal);
        System.out.println(value);

//Insert KEY AND FILE NAME INTO THE HASHMAP

        //-----------------------------------------------------------------------------

            String IPofDest = keyIPMap.get(value); 
            String[] IPport = IPofDest.split(":");
            String IP = IPport[0];
            //int port = Integer.parseInt(IPport[1]);
            //String IP = "172.21.127.39";
            int port = 9898;


            try
            {
                Socket client=new Socket(IP, port);
                System.out.println("Connected to server " + client.getInetAddress()
                        + ": " + client.getPort());
                System.out.println("local port is " + client.getLocalPort());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                int i=0;
                writer.write(fileN);
               // writer.newLine();
                //writer.flush();

                //Writing contents of file
                FileReader fileInput = new FileReader(fileN);
                BufferedReader br = new BufferedReader(fileInput);
                String line = "";
                do
                {
                    line = br.readLine();
                    writer.write(line);
                }while (br.readLine() != null);

                client.close();
            }
            catch (Exception e)
            {
                System.out.println("Error in connection");
            }



        //-----------------------------------------------------------------------------

        // Done at the receiver
        // fileList.add(fileN);
        // fileTable.put(value, fileList);


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

