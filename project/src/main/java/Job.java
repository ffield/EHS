import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//Francis Field

//Record used to keep track of Jobs and their rates
public class Job {
	String jobName;
	Map<String,Double> jobRates;
	Collection<String> jobWorkTypes;
	
	public Job(String name) {
		this.jobRates = new HashMap<String,Double>();
	}
	
	public void addRate(String type, Double amount) {
		this.jobRates.put(type, amount);
	}
	
	public void addWorkType(String work) {
		this.jobWorkTypes.add(work);
	}
}