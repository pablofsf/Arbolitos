package data;

import java.nio.file.Path;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;

public class Arff_Reader {
	
	Instances examples;
	
	//Constructors
	public Arff_Reader(Path path){
		try {
			DataSource examplesSource = new DataSource(path.toString());
			this.examples = examplesSource.getDataSet();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Arff_Reader(String path){
		try {
			DataSource examplesSource = new DataSource(path);
			this.examples = examplesSource.getDataSet();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void print(){
		System.out.println(this.examples);
	}
	
	public static int main(String[] args){
		Arff_Reader lector = new Arff_Reader("./wait.arff");
		
		lector.print();
		
		return 0;
	}
}
