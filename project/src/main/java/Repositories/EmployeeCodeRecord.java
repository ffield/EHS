//Francis Field
package Repositories;

//class to hold first name last name and computer ease employee code

public class EmployeeCodeRecord {
	private String firstName;
	private String lastName;
	private String employeeCode;
	
	public EmployeeCodeRecord(String fn, String ln, String ec) {
		this.firstName = fn;
		this.lastName = ln;
		this.employeeCode = ec;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
}