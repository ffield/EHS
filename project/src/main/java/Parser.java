import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;

import Repositories.ComputerEaseRepository;
import Repositories.EmployeeInfo;
import Repositories.EmployeeRepository;
import Repositories.JobRepository;

//Francis Field


//used to handle import of employee timesheet data from Track output
public class Parser {
	
	JobRepository jobInfo;
	EmployeeRepository employeeInfo;
	ComputerEaseRepository computerEaseInfo;
	String importFilePath;
	CSVReader reader = null;
	ArrayList<Employee> payroll;
	
	
	public Parser(JobRepository jr, EmployeeRepository er, ComputerEaseRepository cer, String path) {
		this.jobInfo = jr;
		this.employeeInfo = er;
		this.computerEaseInfo = cer;
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
            	System.out.println(computerEaseInfo.getComputerEaseEmployeeCodes());
            	e.setComputerEaseID(line[dictionary.get("Employee ID")]);
            	if (!line[dictionary.get("Total Hours")].equals("0")) {
	            		Job job = new Job(line[dictionary.get("Job Number")]);
	            		job.addRate(line[dictionary.get("Labor Type Name")], jobInfo.getInfo(line[dictionary.get("Job Number")]).getWage(line[dictionary.get("Labor Type Name")]));
	            		e.addJob(job);
	            		Role r = new Role(line[dictionary.get("Labor Type Name")], Double.valueOf(line[dictionary.get("Regular Hours")]),line[dictionary.get("Tracking ID")]);
	            		r.setComputerEaseLaborClassCode(computerEaseInfo.getComputerEaseClassCodes().get(r.getLaborTypeName()));
	            		ArrayList<Role> arrayOfRoles = new ArrayList<Role>();
	            		initializeRole(line,r,dictionary);
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
                		r.setComputerEaseLaborClassCode(computerEaseInfo.getComputerEaseClassCodes().get(r.getLaborTypeName()));
                		ArrayList<Role> arrayOfRoles = new ArrayList<Role>();
                		initializeRole(line,r,dictionary);
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
            			r.setComputerEaseLaborClassCode(computerEaseInfo.getComputerEaseClassCodes().get(r.getLaborTypeName()));
            			initializeRole(line,r,dictionary);
            			jobAlreadyWorked.addRate(line[dictionary.get("Labor Type Name")], jobInfo.getInfo(line[dictionary.get("Job Number")]).getWage(line[dictionary.get("Labor Type Name")]));
            			Collection<Role> rolesWorkedByEmployee = e.getRoles(jobAlreadyWorked);
            			rolesWorkedByEmployee.add(r);
            		}
            		
            		}
        		
        	}
        }
    Payroll finalPayroll = new Payroll(payroll);
    finalPayroll.generateOutput();

    } catch (IOException e) {
        e.printStackTrace();
    }
    
	}
	
	
	private static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	//Helper function to parse the line and figure out split of day v night hours
	public void initializeRole(String[] line, Role r, Map<String, Integer> dictionary) {
		r.setDayOfWeek(line[dictionary.get("Day of the Week")]);
		String startTime = line[dictionary.get("Start Time")];
		r.setStartTime(startTime);
		String stopTime = line[dictionary.get("Stop Time")];
		r.setStopTime(stopTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		LocalTime startTimeObject = LocalTime.parse(startTime, formatter);
		LocalTime stopTimeObject = LocalTime.parse(stopTime, formatter);
		LocalTime nightHours = LocalTime.parse("7:00 PM", formatter);
		LocalTime nightHoursBound = LocalTime.parse("5:00 AM", formatter);
		LocalTime midnight = LocalTime.parse("11:59 PM",formatter);
		LocalTime noon = LocalTime.parse("12:00 PM",formatter);
		LocalTime morning = LocalTime.parse("12:00 AM",formatter);
		if (((startTimeObject.isAfter(noon) && stopTimeObject.isBefore(noon)))){
			Double dayHourDuration =(double) Duration.between(startTimeObject, nightHours).toMinutes();
			Double nightHourDuration = 0.0;
			if (dayHourDuration > 0.0) {
			dayHourDuration = dayHourDuration / 60.0;
			nightHourDuration = (double) Duration.between(startTimeObject, midnight).toMinutes();
			nightHourDuration = nightHourDuration / 60.0;
			if (stopTimeObject.isAfter(nightHoursBound)) {
				Double nextDayHoursDuration = (double) Duration.between(nightHoursBound, stopTimeObject).toMinutes();
				nextDayHoursDuration = nextDayHoursDuration / 60.0;
				dayHourDuration += nextDayHoursDuration;
				Double nextNightHoursDuration = (double) Duration.between(morning, nightHoursBound).toMinutes();
				nextNightHoursDuration = nextNightHoursDuration / 60.0;
				nightHourDuration += nextNightHoursDuration;
			}
			else {
				Double nextNightHoursDuration = (double) Duration.between(morning, stopTimeObject).toMinutes();
				nextNightHoursDuration = nextNightHoursDuration / 60.0;
				nightHourDuration += nextNightHoursDuration;
			}
			}
			else {
				dayHourDuration = 0.0;
			}
			if (startTimeObject.isAfter(nightHours) || startTimeObject.equals(nightHours)) {
			nightHourDuration = (double) Duration.between(startTimeObject, midnight).toMinutes();
			nightHourDuration = nightHourDuration / 60.0;
			if (stopTimeObject.isAfter(nightHoursBound)) {
				Double nextDayHoursDuration = (double) Duration.between(nightHoursBound, stopTimeObject).toMinutes();
				nextDayHoursDuration = nextDayHoursDuration / 60.0;
				dayHourDuration += nextDayHoursDuration;
				Double nextNightHoursDuration = (double) Duration.between(morning, nightHoursBound).toMinutes();
				nextNightHoursDuration = nextNightHoursDuration / 60.0;
				nightHourDuration += nextNightHoursDuration;
			}
			else {
				Double nextNightHoursDuration = (double) Duration.between(morning, stopTimeObject).toMinutes();
				nextNightHoursDuration = nextNightHoursDuration / 60.0;
				nightHourDuration += nextNightHoursDuration;
			}
			}
			r.setNightHours(round(nightHourDuration,1));
			r.setDayHours(round(dayHourDuration,1));
		}
		else if (((stopTimeObject.isAfter(nightHours)))){
			Double dayHourDuration =(double) Duration.between(startTimeObject, nightHours).toMinutes();
			if (dayHourDuration > 0) {
			dayHourDuration = dayHourDuration / 60.0;
			}
			else {
				dayHourDuration = 0.0;
			}
			Double nightHourDuration = (double) Duration.between(nightHours, stopTimeObject).toMinutes();
			nightHourDuration = nightHourDuration / 60.0;
			r.setNightHours(round(nightHourDuration,1));
			r.setDayHours(round(dayHourDuration,1));
		}
//		else if ((startTimeObject.equals(nightHours) && stopTimeObject.isBefore(midnight))) {
//			Double dayHourDuration = 0.0;
//			Double nightHourDuration =(double) Duration.between(startTimeObject, stopTimeObject).toMinutes();
//			nightHourDuration = nightHourDuration/ 60.0;
//			
//		}
		else {
			Double dayHourDuration =(double) Duration.between(startTimeObject, nightHours).toMinutes();
			Double nightHourDuration = 0.0;
			if (dayHourDuration > 0.0) {
			dayHourDuration = dayHourDuration / 60.0;
			}
			else {
				dayHourDuration = 0.0;
			}
			r.setNightHours(round(nightHourDuration,1));
			r.setDayHours(round(dayHourDuration,1));
		}
	}
	
}