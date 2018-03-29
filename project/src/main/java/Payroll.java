import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import com.opencsv.CSVWriter;

//Francis Field

//class used to keep track of all Employees and compensation

public class Payroll {
	Collection<Employee> employees;
	
	
	public Payroll(Collection<Employee> generatedPayroll) {
		this.employees = generatedPayroll;
	}
	
	public void printOutput(){
		for (Employee e: employees) {
			System.out.println("EMPLOYEE: " + e.getFullName());
			Double totalHours = 0.0;
			for (Job j : e.jobsWorkedByEmployee) {
				if (e.getRoles(j)!=null) {
					for (Role r : e.getRoles(j)) {
						if (!r.trackingId.equals("LUNCH") && !r.trackingId.equals("Lunch-LUNCH")) {
						totalHours += r.hoursWorked;
						System.out.println(r.hoursWorked + " hours worked at job " + j.jobName + " doing task " + r.trackingId + " as " + r.laborTypeName);
						System.out.println("Prevailing wage for this role and job is " + calculatePrevailingWage(e,j,r));
						System.out.println("DOW: " + r.getDayOfWeek());
						
						}
					}
					
				}
			}
			System.out.println("TOTAL HOURS: " + totalHours);
		}
	}
	
	public Double calculatePrevailingWage( Employee employee, Job job, Role role ) {
			System.out.println("Job: " + job.jobName);
			Double rateForRole = job.getRate(role.getLaborTypeName());
			Double healthInsuranceAddition = employee.getHealthInsuranceAddition();
			Double dentalInsuranceAddition = employee.getDentalInsuranceAddition();
			Double vacationAddition = employee.getVacationAddition();
			Double holidayAddition = employee.getHolidayAddition();
			Double match401K = employee.get401kMatch(job, role.getLaborTypeName());
			Double prevailingWage = rateForRole + healthInsuranceAddition + dentalInsuranceAddition + vacationAddition + holidayAddition + match401K;
			return prevailingWage;
			}
	

	public void generateOutput() throws IOException {
//	            Writer writer = Files.newBufferedWriter(Paths.get("../out.csv"));
//
//	            CSVWriter csvWriter = new CSVWriter(writer,
//	                    CSVWriter.DEFAULT_SEPARATOR,
//	                    CSVWriter.NO_QUOTE_CHARACTER,
//	                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
//	                    CSVWriter.DEFAULT_LINE_END);
	            
	            CSVWriter csvWriter = new CSVWriter(new FileWriter("data.csv"));
	            
	            String[] headerRecord = {"Name", "Day", "Job", "Tracking ID", "Labor Type", "Rate", "Prevailing Wage",
	            		"Start Time","Stop Time","Hours Worked","Night Hours","Day Hours"};
	            csvWriter.writeNext(headerRecord);
	            
	            for (Employee e: employees) {
	    			String name = e.getFullName();
//	    			Double totalHours = 0.0;
	    			for (Job j : e.jobsWorkedByEmployee) {
	    				if (e.getRoles(j)!=null) {
	    					for (Role r : e.getRoles(j)) {
	    						if (!r.trackingId.equals("LUNCH") && !r.trackingId.equals("Lunch-LUNCH")) {
	    							String day = r.getDayOfWeek();
	    							String job = j.jobName;
	    							String trackingId = r.trackingId;
	    							String laborType = r.getLaborTypeName();
	    							Double rate = j.getRate(r.laborTypeName);
	    							Double prevailingWage = calculatePrevailingWage(e,j,r);
	    							String startTime = r.getStartTime();
	    							String stopTime = r.getStopTime();
	    							Double hours = r.getHoursWorked();
	    							Double nightHours = r.getNightHours();
	    							Double dayHours = r.getDayHours();
	    							String[] record = {name,day,job,trackingId,laborType,String.valueOf(rate),String.valueOf(prevailingWage),
	    									startTime,stopTime,String.valueOf(hours),String.valueOf(nightHours),String.valueOf(dayHours)};
	    							csvWriter.writeNext(record);
	    							}
	    						
	    						}
	    					}
	    					
	    				}
	    			}
	    			csvWriter.close();
	    		}
	}
	
