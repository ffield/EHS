import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;

import Repositories.EmployeeInfo;
import Repositories.EmployeeRepository;
import Repositories.JobRepository;

//Francis Field


//used to handle import of employee timesheet data from Track output
public class Parser {
	
	JobRepository jobInfo;
	EmployeeRepository employeeInfo;
	String importFilePath;
	CSVReader reader = null;
	ArrayList<Employee> payroll;
	
	
	public Parser(JobRepository jr, EmployeeRepository er, String path) {
		this.jobInfo = jr;
		this.employeeInfo = er;
		this.importFilePath = path;
	}
	
	
	
	public void initializeData() {
    try {
    	//Initialiing CSV Reader
        reader = new CSVReader(new FileReader(importFilePath));
        //INitializing variables to find columns
        Set<String> values = new HashSet<String>();
        Set<String> collectedEmployees = new HashSet<String>();
        payroll = new ArrayList<Employee>();
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        
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
            	if (!line[dictionary.get("Total Hours")].equals("0")) {
	            		Job job = new Job(line[dictionary.get("Job Number")]);
	            		job.addRate(line[dictionary.get("Labor Type Name")], jobInfo.getInfo(line[dictionary.get("Job Number")]).getWage(line[dictionary.get("Labor Type Name")]));
	            		e.addJob(job);
	            		Role r = new Role(line[dictionary.get("Labor Type Name")], Double.valueOf(line[dictionary.get("Regular Hours")]),line[dictionary.get("Tracking ID")]);
	            		ArrayList<Role> arrayOfRoles = new ArrayList<Role>();
	            		arrayOfRoles.add(r);
	            		e.logRole(job,arrayOfRoles);
            		}
            	EmployeeInfo ei = employeeInfo.getInfo(e.getFullName());
            	e.basePayRate = ei.getBaseRate();
            	e.dentalInsuranceCost = ei.getDentalInsurance();
            	e.medicalInsuranceCost = ei.getMedicalInsurance();
            	e.percentage401k = ei.getMatch401k();
            	e.yearsWorked = ei.getYearsWorked();
            	payroll.add(e);
            	}
        	else {
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
            			job.addRate(line[dictionary.get("Labor Type Name")], jobInfo.getInfo(line[dictionary.get("Job Number")]).getWage(line[dictionary.get("Labor Type Name")]));
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
            			jobAlreadyWorked.addRate(line[dictionary.get("Labor Type Name")], jobInfo.getInfo(line[dictionary.get("Job Number")]).getWage(line[dictionary.get("Labor Type Name")]));
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