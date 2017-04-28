package chord;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;

public class ClassAdd
{
    public int addNode(String host, TreeMap<Integer, String> keyIPmap) {
        //Hash IP:Port to get ID

        //Check if first node
        //If its first node, call classCreate
        // return first node ID to main

        //If its not first node- call createFinger class

        //return ID to main
    	System.out.println("in ClassAdd");
        String hashedNodeValue = hashFunction(host.getBytes(),"SHA-256");
        String digits = "0123456789ABCDEF";
        hashedNodeValue = hashedNodeValue.toUpperCase();
        int nodeValDec = 0, nodeVal = 0;
        for (int i = 0; i < hashedNodeValue.length(); i++)
        {
            char c = hashedNodeValue.charAt(i);
            int d = digits.indexOf(c);
            nodeValDec = 16*nodeValDec + d;
        }
        int m = 7; //Determines maximum of nodes; Can be set by user

        int maxActiveNode = 1;
        int activeNode;
        //Calculating 2 power m.
        for (int i=0; i<m; i++)
        {
            maxActiveNode *= 2;
        }
        //Truncated hashed value within range of 2^m
        nodeVal = Math.abs(nodeValDec)%maxActiveNode;
        MainClass.keyIPMap.put(nodeVal, host);
        System.out.println("in addclass.........");
        //	System.out.println("NodeVal"+nodeVal);
        MainClass.leader =  MainClass.keyIPMap.lastEntry().getValue();
        return nodeVal;

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
