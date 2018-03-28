import static org.junit.Assert.*;

import org.junit.Test;

//Francis Field

public class JobTest {
	
	Job testJob  = new Job("100");
	
	@Test 
	public void testAddRate() {
		testJob.addRate("Carpenter",30.37);
		assertEquals(testJob.jobRates.size(),1);
		Double getRateResult = testJob.getRate("Carpenter");
		assertEquals(getRateResult,(Double) 30.37);
	}
	
	@Test
	public void testWorkTypes() {
		assertEquals(0,testJob.getWorkTypes().size());
		testJob.addWorkType("Carpenter");
		testJob.addWorkType("Foreman");
		assertEquals(2,testJob.getWorkTypes().size());
	}
	
}