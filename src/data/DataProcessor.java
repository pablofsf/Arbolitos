package data;

import java.nio.file.Path;
import java.util.Enumeration;



public class DataProcessor {
	
	Instances examples;
	
	//Constructors
	public DataProcessor(Path path){
		try {
			DataSource examplesSource = new DataSource(path.toString());
			this.examples = examplesSource.getDataSet();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DataProcessor(String path){
		try {
			DataSource examplesSource = new DataSource(path);
			this.examples = examplesSource.getDataSet();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
