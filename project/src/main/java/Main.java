import java.awt.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
            Set<String> collectedEmployees = new HashSet<String>();
            ArrayList<Employee> payroll = new ArrayList<Employee>();
            Map<String,Integer> dictionary = new HashMap<String,Integer>();
            String[] desiredValues = {"Employee ID","Employee First Name","Employee Last Name","Start Time","Stop Time","Work Hours","Job Number","Labor Type Name","Tracking ID"};
            for ( String v : desiredValues ) {
            	values.add(v);
            }
            
            //iterate through csv, finding indices of desired values first,
            String[] line;
            line = reader.readNext();;
            for (int i = 0; i < line.length; i++) {
        		dictionary.put(line[i],i);
            }
        		
        	//iterate through rest, collecting info
            while ((line = reader.readNext()) != null) {
            	String fullname = line[dictionary.get("Employee First Name")] + " " + line[dictionary.get("Employee Last Name")];
            	if (!collectedEmployees.contains(fullname)){
	            	collectedEmployees.add(fullname);
	            	Employee e = new Employee(line[dictionary.get("Employee First Name")],line[dictionary.get("Employee Last Name")]);
	            	payroll.add(e);
	            	if (!line[dictionary.get("Total Hours")].equals("0")) {
		            		Job job = new Job(line[dictionary.get("Job Number")]);
		            		e.addJob(job);
		            		Role r = new Role(line[dictionary.get("Labor Type Name")], Double.valueOf(line[dictionary.get("Regular Hours")]),line[dictionary.get("Tracking ID")]);
		            		ArrayList<Role> arrayOfRoles = new ArrayList<Role>();
		            		arrayOfRoles.add(r);
		            		e.logRole(job,arrayOfRoles);
	            		}
	            	}
            	else {
            		System.out.println("Found repeat employee");
            		//get employee from payroll
            		Employee e = null;
            		for ( Employee employee: payroll) {
            			if (employee.getFullName().equals(fullname) ) {
            				e = employee;
            				break;
            			}
            		}
                	if (!line[dictionary.get("Total Hours")].equals("0")) {
                		if (!e.jobPresent(line[dictionary.get("Job Number")])) {
                			Job job = new Job(line[dictionary.get("Job Number")]);
                    		e.addJob(job);
                    		Role r = new Role(line[dictionary.get("Labor Type Name")], Double.valueOf(line[dictionary.get("Regular Hours")]),line[dictionary.get("Tracking ID")]);
                    		ArrayList<Role> arrayOfRoles = new ArrayList<Role>();
                    		arrayOfRoles.add(r);
                    		e.logRole(job,arrayOfRoles);
                		}
                		else {
                			Job jobAlreadyWorked = null;
                			for (Job jobWorked : e.jobsWorkedByEmployee) {
                				if (jobWorked.jobName.equals(line[dictionary.get("Job Number")])){
                					jobAlreadyWorked = jobWorked;
                					break;
                				}
                			}
                			Role r = new Role(line[dictionary.get("Labor Type Name")], Double.valueOf(line[dictionary.get("Regular Hours")]),line[dictionary.get("Tracking ID")]);
                			Collection<Role> rolesWorkedByEmployee = e.getRoles(jobAlreadyWorked);
                			rolesWorkedByEmployee.add(r);
                		}
                		
                		}
            		
            	}
            }
        Payroll finalPayroll = new Payroll(payroll);
        finalPayroll.printOutput();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
	}
	
}