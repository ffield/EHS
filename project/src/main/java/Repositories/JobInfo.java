//Representation of job
package Repositories;

import java.util.HashMap;
import java.util.Map;

public class JobInfo {
	String jobID;
	Map<String,Double> wages;
	
	public JobInfo(String id) {
		this.jobID = id;
		this.wages = new HashMap<String,Double>();
	}
	
	public void addWage(String role, Double wage) {
		this.wages.put(role,wage);
	}
	
	public Double getWage(String role) {
		return this.wages.get(role);
	}
	
}