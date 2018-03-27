//Francis Field

//representation of hours worked at each role
public class Role {
	String name;
	Integer hoursWorked;
	
	public Role(String nname, Integer worked) {
		this.name = nname;
		this.hoursWorked = worked;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getHoursWorked() {
		return this.hoursWorked;
	}
}