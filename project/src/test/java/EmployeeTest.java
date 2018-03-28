import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

//Francis Field

public class EmployeeTest {
	
	Employee testEmployee = new Employee("Francis","Field");
	
	@Test
	public void testFullName() {
		String fullName = testEmployee.getFullName();
		assertEquals(fullName, "Francis Field");
	}
	
	@Test
	public void testJobPresent() {
		Job testJob = new Job("100");
		assertFalse(testEmployee.jobPresent("100"));
		testEmployee.addJob(testJob);
		assertTrue(testEmployee.jobPresent("100"));
	}
	
	@Test
	public void testAddJob() {
		Job testJob = new Job("100");
		assertTrue(testEmployee.jobsWorkedByEmployee.isEmpty());
		testEmployee.addJob(testJob);
		assertEquals(1,testEmployee.jobsWorkedByEmployee.size());
	}
	
	@Test
	public void testRoles() {
		Role testRole = new Role("Carpenter",3.5,"100");
		assertEquals(0,testEmployee.rolesPerformedByEmployee.size());
		Job testJob = new Job("100");
		testEmployee.addJob(testJob);
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(testRole);
		testEmployee.logRole(testJob, roles);
		assertEquals(1,testEmployee.rolesPerformedByEmployee.size());
	}
	
	
}