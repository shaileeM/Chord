package chord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class DeleteFile {
    public void DeleteSearch(String fileName,HashMap<Integer, ArrayList<String>> fileTable,TreeMap<Integer,String> keyIPMap){
        String fileSearch = fileName;
        ArrayList<Integer> list_keys = new ArrayList<> (fileTable.keySet());
        int found=0;
        String value_in_list;
        for (int i = 0; i < list_keys.size(); i++) {
            System.out.println(list_keys.get(i));
            int key = list_keys.get(i);
            //for each key , get the values_array in the map
            ArrayList<String> list_values = new ArrayList<> (fileTable.get(key));
            for (int j = 0; j < list_values.size(); j++){
                System.out.println(list_values.get(j));
                value_in_list =list_values.get(j);
                if(value_in_list.equals(fileName))
                    found = key;
            }
        }
        System.out.println("The key with the file name "+fileName+":"+found);
        ArrayList<String> deletion_array=fileTable.get(found);
        for(int i=0;i<deletion_array.size();i++){
            System.out.println("before deletion");
            System.out.println(deletion_array.get(i));
        }
        deletion_array.remove(fileSearch);
        for(int i=0;i<deletion_array.size();i++){
            System.out.println("after deletion");
            System.out.println(deletion_array.get(i));
        }
        fileTable.put(found, deletion_array);

    }}



