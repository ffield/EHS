import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//Francis Field


//Used to keep track of an employees hours worked on each day of the week
//Keeps track of night hours and such 
public class Day {
	String fullName;
	Double dayHours;
	Double nightHours;
	Double totalHours;
	Map<Job,Collection<Role>> rolesPerformedByEmployee;
	
	public Day(String fn, Map<Job,Collection<Role>> roles) {
		this.fullName = fn;
		this.dayHours = 0.0;
		this.nightHours = 0.0;
		this.totalHours = 0.0;
		this.rolesPerformedByEmployee = roles;
	}
	
	public void addDayHours(Double hours) {
		this.dayHours += hours;
		this.totalHours += hours;
	}
	
	public void addNightHours(Double hours) {
		this.nightHours += hours;
		this.totalHours += hours;
	}
	
	
	
}