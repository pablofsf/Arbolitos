package tree;

import java.util.Arrays;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.Attribute;

public class TrainTree{

    Tree DecisionTree(examples, attributes, parent){

        if()
    }
    /*function DECISION-TREE-LEARNING(examples,attributes,parent examples)
    returns a tree
    if examples is empty then return PLURALITY-VALUE(parent examples)
        else if all examples have the same classiﬁcation then returnthe classiﬁcation
            else if attributes is empty then return PLURALITY-VALUE(examples)
            else
                A←argmaxa ∈ attributes IMPORTANCE(a,examples)
                tree ←a new decision tree with root test A
                foreach value vk of A
    do exs ←{e : e∈examples and e.A = vk} subtree ←DECISION-TREE-LEARNING(exs,attributes −A,examples)
    add a branch to tree with label (A = vk) and subtree subtree
    return tree
    */
    
    private static double log2(double val){
    	return Math.log(val)/Math.log(2);
    }
    
    //Taken a certain group of data, where several decisions can be made, check the probability of a certain one
    private double ProbabilityDecision(Instances examples,int decision){
    	int decisionsTrue = 0;
    	
    	double[] decisionsArray = examples.attributeToDoubleArray(examples.numAttributes()-1);
    	for(int i = 0; i < examples.numInstances(); i++){
    		if(decision == (int) decisionsArray[i]){
    			decisionsTrue++;
    		}
    	}
    	
    	return decisionsTrue/examples.numInstances();
    }
    
    //Given a certain data, compute its entropy
    private double Entropy(Instances examples){
    	double entropy = 0;
    	double probability;
    	
    	for(int i = 0; i < examples.attribute(examples.numAttributes()-1).numValues(); i++){
    		if((probability = ProbabilityDecision(examples,i)) != 0)
    			entropy -= probability*log2(probability);
    	}
    	
    	return entropy;
    }
    
    //Given a certain data, the remainder is calculated this way:
    //Split the data into sets according to the different values of the attribute you are given
    //For each set, calculate its probability and multiply per the entropy of that set. Add all of them
    private double Remainder(Instances examples, Attribute attribute){
    	double remainder = 0;
    	
    	double[] attributeExampleValues = examples.attributeToDoubleArray(attribute.index());
    	for(int i = 0; i < (int) attribute.numValues(); i++){
        	
    		//This loop and variable could be placed into a function, together with the double[]
    		Instances reducedExamples = new Instances(examples);

    		for(int j = 0; j < attributeExampleValues.length; j++){
    			if((int) attributeExampleValues[j] != i){
    				reducedExamples.delete(j);
    			}
    		}
    		
    		remainder += (reducedExamples.numInstances()/examples.numInstances())*Entropy(reducedExamples);
    	}
    	
    	return remainder;
    }
    
    
    private Attribute Importance(Instances examples){
    	double maxGain = 0, entropy = Entropy(examples);
    	Attribute bestAttribute;
    	
    	for(int i = 0; i < (int) examples.numAttributes(); i++){
    		double gain = entropy -  Remainder(examples,examples.attribute(i));
    		if(maxGain < gain){
    			maxGain = gain;
    			bestAttribute = examples.attribute(i);
    		}
    	}
    	
    	return bestAttribute;
    }


}