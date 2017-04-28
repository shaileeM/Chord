package chord;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.*;
import java.util.Map;
import java.util.TreeMap;

 
public class CreateFinger
{
	
	
	
	HashMap getHashMap(){
		return fingerTable1;
	}
	
	public static HashMap<Integer, Integer> fingerTable1 ;
	
	    public void fingerTable(int nodeID, TreeMap<Integer,String> keyIPMap)
    {
        //Construct finger table
      //HashMap<Integer, Integer> fingerTable1 = new HashMap<>();
        //System.out.println("ClassFinger"+nodeID);
        // String fingerTableEntry = nodeID + "%"+ host ;
	    	fingerTable1 = new HashMap<>();
	    	System.out.println("NODEID"+nodeID);
	    	for(int i =0; i<7 ;i++){
            int val =(int) Math.pow(2.0,i);
            int nodeval=(nodeID+ val)%128;
            int value ;
            if( nodeval > keyIPMap.lastKey())
                value = keyIPMap.firstKey();
            else value = keyIPMap.ceilingKey(nodeval);
            System.out.println("I VALUE   "+i);
            System.out.println("nodeval   "+nodeval);
            System.out.println("CeilValue    "+keyIPMap.ceilingKey(nodeval));
            System.out.println("Value Entered    "+value);
            this.fingerTable1.put(i,value );
        	System.out.println("size in CreateFinger"+fingerTable1.size());

        }
        for(Map.Entry<Integer, Integer> entry : fingerTable1.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key);
            System.out.println(value);
            // do what you have to do here
            // In your case, an other loop.
        }
        System.out.println("in fingerclass.........");

    }

   


    //Find N+2^i mod 2^m to get a value

    //Search if the value if equal to an existing ID - if it is then that is the successor
    //Otherwise get the next greatest value from the global array list and that is the successor



}
