import java.util.HashMap;
import java.util.Map;

//Francis Field

//Record used to keep track of Jobs and their rates
public class Job {
	String jobName;
	Map<String,Double> jobRates;
	String[] jobWorkTypes;
	
	public Job(String[] types) {
		this.jobRates = new HashMap<String,Double>();
		this.jobWorkTypes = types;
	}
	
	public void addRate(String type, Double amount) {
		this.jobRates.put(type, amount);
	}
}