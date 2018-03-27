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
			System.out.println(e.getFullName());
			for (Job j : e.jobsWorkedByEmployee) {
				if (e.getRoles(j)!=null) {
					for (Role r : e.getRoles(j)) {
						System.out.println(r.hoursWorked + " hours worked at job " + r.trackingId + " as" + r.name);
					}
				}
			}
		}
	}
}