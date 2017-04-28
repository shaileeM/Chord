package chord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class FileSearch {

    public void FileSearch(String fileName,HashMap<Integer, ArrayList<String>> fileTable,TreeMap<Integer,String> keyIPMap){

        // Get the file name for search - DONE
        // Iterate and check for the file name in the arraylist
        // Find the key corresponding to the file -- this gives the successor ID
        // SYSOUT
        // With ID display the ip address

        String fileSearch = fileName;

        //get key values of fileTable -> array
        // for each key iterate value == value_result
        // if true return key
        //25 - f1,f2
        ArrayList<Integer> list_keys = new ArrayList<> (fileTable.keySet());
        int found=0;
        String value_in_list;
        //get keys present in map
        for (int i = 0; i < list_keys.size(); i++) {
            System.out.println(list_keys.get(i));
            int key = list_keys.get(i);
            //for each key , get the values_array in the map
            ArrayList<String> list_values = new ArrayList<> (fileTable.get(key));
            for (int j = 0; j < list_values.size(); j++){
                System.out.println(list_values.get(j));
                value_in_list =list_values.get(j);
                //System.out.println(fileName);
                //System.out.println(value_in_list);
                //for each value, check if value equals to filename; if found, then return the respective key
                if(value_in_list.equals(fileName))
                    found = key;
            }
        }
        System.out.println("The key with the file name "+fileName+":"+found);

        String File_location=keyIPMap.get(found);
        System.out.println("IP LOCATION"+File_location);

        /**
         ArrayList<Integer> tree_list_keys = new ArrayList<> (keyIPMap.keySet());
         int found_tree_node=0;
         for (int i = 0; i < tree_list_keys.size(); i++) {
         System.out.println(tree_list_keys.get(i));
         int key = list_keys.get(i);

         //for each key , get the values_array in the map
         ArrayList<String> list_values = new ArrayList<> (fileTable.get(key));
         for (int j = 0; j < list_values.size(); j++){
         System.out.println(list_values.get(j));
         value_in_list =list_values.get(j);
         //System.out.println(fileName);
         //System.out.println(value_in_list);
         //for each value, check if value equals to filename; if found, then return the respective key
         if(value_in_list.equals(fileName))
         found = key;
         }
         }
         */
    }}
