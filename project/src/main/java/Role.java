//Francis Field

//representation of hours worked at each role
public class Role {
	String name;
	Double hoursWorked;
	String trackingId;
	
	public Role(String nname, Double worked, String trackingID) {
		this.name = nname;
		this.hoursWorked = worked;
		this.trackingId = trackingID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Double getHoursWorked() {
		return this.hoursWorked;
	}
}