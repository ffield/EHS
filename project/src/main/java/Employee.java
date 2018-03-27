import java.util.HashMap;
import java.util.Map;

//Francis Field

//representation of employee
public class Employee {
	String firstName;
	String lastName;
	Job[] jobsWorkedByEmployee;
	Map<Job,Role> rolesPerformedByEmployee = new HashMap<Job,Role>();
	double percentage401k;
	
	public Employee (String fn, String ln, Job[] jobs, Map<Job,Role> rolesPerformed,double recordedPercentage401k) {
		this.firstName = fn;
		this.lastName = ln;
		this.jobsWorkedByEmployee = jobs;
		this.rolesPerformedByEmployee = rolesPerformed;
		this.percentage401k = recordedPercentage401k;
	}
	
	public String toString() {
		String fullname = this.firstName + " " + this.lastName;
		return fullname;
	}
	
	
	
	
}