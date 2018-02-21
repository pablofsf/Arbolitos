package tree;

import java.util.ArrayList;

import data.Attribute;

public class PrintTree {

	public PrintTree(String path, Node tree){
		
	}
	
	public PrintTree(Node tree, int depth){
		Attribute attribute = tree.getAtribute();
		ArrayList<String> attributeValues = attribute.getValues();
		
		for(int i = 0; i < attribute.numValues(); i++){
			System.out.print(attribute.getName() + " = " + attributeValues.get(i));
		}
	}
}
