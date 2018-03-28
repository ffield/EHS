//Francis Field

//representation of hours worked at each role
public class Role {
	String laborTypeName;
	Double hoursWorked;
	String trackingId;
	
	public Role(String nname, Double worked, String trackingID) {
		this.laborTypeName = nname;
		this.hoursWorked = worked;
		this.trackingId = trackingID;
	}
	
	public String getLaborTypeName() {
		return this.laborTypeName;
	}
	
	public Double getHoursWorked() {
		return this.hoursWorked;
	}
}