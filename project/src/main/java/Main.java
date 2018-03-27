import java.awt.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;

//Francis Field

public class Main {
	
	public static void main(String[] args) {
		
        String csvFile = "src/main/resources/test1.csv";

        CSVReader reader = null;
        try {
        	//Initialiing CSV Reader
            reader = new CSVReader(new FileReader(csvFile));
            
            //INitializing variables to find columns
            ArrayList<Integer> indices = new ArrayList<Integer>();
            Set<String> values = new HashSet<String>();
            Map<Integer,String> dictionary = new HashMap<Integer,String>();
            String[] desiredValues = {"Employee ID","Employee First Name","Employee Last Name","Start Time","Stop Time","Work Hours","Job Number","Labor Type Name","Tracking ID"};
            for ( String v : desiredValues ) {
            	values.add(v);
            }
            
            //iterate through csv, finding indices of desired values first
            String[] line;
            line = reader.readNext();
            System.out.print(line.toString());
            for (int i = 0; i < line.length; i++) {
        		if (values.contains(line[i])) {
        			indices.add(i);
        			dictionary.put(i, line[i]);
        		}
            }
        		
        	//iterate through rest, collecting info
            while ((line = reader.readNext()) != null) {
            	String output = "";
            	for (int j = 0; j < indices.size(); j++) {
            		output = output + " " + dictionary.get(indices.get(j)) + ": " + line[indices.get(j)];
            	}
            	System.out.println(output);
            	}
    
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
	}
	
}