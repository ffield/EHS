import java.util.Collection;

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
	}
	
