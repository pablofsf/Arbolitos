package data;

import java.nio.file.Path;
import java.util.Enumeration;

import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;


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
	
	public void remove(){
		Instance example1 = examples.instance(0);
		double[] values3 = examples.attributeToDoubleArray(0);
		//System.out.print(values3[0].intValue());
		double[] values = example1.toDoubleArray();
		System.out.println(examples.attribute("Price").value(examples.attribute("Price").numValues()-1));
		//Attribute wait1 = example1.attributeSparse(5);
		Enumeration jaja = examples.instance(0).enumerateAttributes();
		System.out.print(values);
	}
	
	public static void main(String[] args){
		Arff_Reader lector = new Arff_Reader("/home/pablo/data/Informatica/IDTrees/src/data/wait.arff");
		
		//lector.remove();
		lector.print();
	}
}
