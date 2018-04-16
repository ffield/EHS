import java.awt.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;

import Repositories.ComputerEaseRepository;
import Repositories.EmployeeInfo;
import Repositories.EmployeeRepository;
import Repositories.JobRepository;

//Francis Field

public class Main {
	
	public static void main(String[] args) {
		
		
		JobRepository jobInfo = new JobRepository("src/main/resources/jobs.csv");
		EmployeeRepository employeeInfo = new EmployeeRepository("src/main/resources/employees.csv");
		ComputerEaseRepository computerEaseInfo = new ComputerEaseRepository("src/main/resources/computereaseClassCodes.csv","src/main/resources/computereaseIDS.csv");
        String csvFile = "src/main/resources/test3.csv";
        Parser importParser = new Parser(jobInfo, employeeInfo,computerEaseInfo, csvFile);
		importParser.initializeData();
		
		
	}
	
}