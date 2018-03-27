import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//Francis Field

//representation of employee
public class Employee {
	String firstName;
	String lastName;
	Collection<Job> jobsWorkedByEmployee;
	Map<Job,Collection<Role>> rolesPerformedByEmployee = new HashMap<Job,Collection<Role>>();
	double percentage401k;
	
	public Employee (String fn, String ln) {
		this.firstName = fn;
		this.lastName = ln;
		this.jobsWorkedByEmployee = new ArrayList<Job>();
//		this.rolesPerformedByEmployee = rolesPerformed;
//		this.percentage401k = recordedPercentage401k;
	}
	
	public String toString() {
		String fullname = this.firstName + " " + this.lastName;
		return fullname;
	}
	
	public String getFullName() {
		String fullname = this.firstName + " " + this.lastName;
		return fullname;
	}
	
	public boolean jobPresent(String jnumber) {
		for (Job job : jobsWorkedByEmployee) {
			if (job.jobName == jnumber) {
				return true;
			}
		}
		return false;
//		if (!jobsWorkedByEmployee.contains(j)){
//			return false;
//		}
//		else {
//			return true;
//		}
	}
	
	public void addJob(Job j) {
		this.jobsWorkedByEmployee.add(j);
	}
	
	public void logRole(Job j, Collection<Role> role) {
		this.rolesPerformedByEmployee.put(j, role);
	}
	
	public Collection<Role> getRoles(Job j) {
		return this.rolesPerformedByEmployee.get(j);
	}
	
	public Collection<Job> getJobsWorkedByEmployee() {
		return this.jobsWorkedByEmployee;
	}
	
	
	
	
}