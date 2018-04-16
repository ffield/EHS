//Francis Field
package Repositories;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;

//Repository to hold information needed for ComputerEase import
public class ComputerEaseRepository {
	
	private Map<String, String> computerEaseClassCodes;
	private Map<String, EmployeeCodeRecord> computerEaseEmployeeCodes;
	private CSVReader classCodeReader;
	private CSVReader employeeCodeReader;
	
	public ComputerEaseRepository(String classCodePath, String employeeCodePath) {
		try {
			computerEaseClassCodes = new HashMap<String,String>();
			computerEaseEmployeeCodes = new HashMap<String, EmployeeCodeRecord>();
			
        	//Initialiing CSV Reader
            classCodeReader = new CSVReader(new FileReader(classCodePath));
            Map<String,Integer> classCodeDictionary = new HashMap<String,Integer>();
            
            employeeCodeReader = new CSVReader(new FileReader(employeeCodePath));
            Map<String,Integer> employeeCodeDictionary = new HashMap<String, Integer>();
            
            
            //iterate through csv, finding indices of desired values first,
            String[] classLine;
            classLine = classCodeReader.readNext();
            for (int i = 0; i < classLine.length; i++) {
        		classCodeDictionary.put(classLine[i],i);
            }
            
            String[] employeeLine;
            employeeLine = employeeCodeReader.readNext();
            for (int i = 0; i < employeeLine.length; i++) {
            	employeeCodeDictionary.put(employeeLine[i],i);
            }
        		
        	//iterate through rest, collecting info
            while ((classLine = classCodeReader.readNext()) != null) {
            	computerEaseClassCodes.put(classLine[classCodeDictionary.get("Class")], classLine[classCodeDictionary.get("Code")]);
            }
            
            while ((employeeLine = employeeCodeReader.readNext()) != null) {
            	String fullName = employeeLine[employeeCodeDictionary.get("First Name")] + " " + employeeLine[employeeCodeDictionary.get("Last Name")];
            	computerEaseEmployeeCodes.put(fullName,new EmployeeCodeRecord(employeeLine[employeeCodeDictionary.get("First Name")],employeeLine[employeeCodeDictionary.get("Last Name")],employeeLine[employeeCodeDictionary.get("ID")]));
            }
            	
    
        } catch (IOException e) {
            e.printStackTrace();
     }
	}

	public Map<String, String> getComputerEaseClassCodes() {
		return computerEaseClassCodes;
	}

	public Map<String, EmployeeCodeRecord> getComputerEaseEmployeeCodes() {
		return computerEaseEmployeeCodes;
	}
	
}