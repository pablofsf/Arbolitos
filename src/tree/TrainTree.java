package tree;

import java.util.ArrayList;
import java.util.Random;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.Attribute;


public class TrainTree{

	//For the 3 comments that say "//This has to be transformed into a end node", the result returned is an integer/double
	//that represents that represents a value for the attribute we are looking for.
	//In the example wait.arff, the attribute that we are looking for is @attribute wait {yes,no}
	//Then, returning 0 means wait = yes and returning 1 means wait = no
	//Both the number or the string could be stored in the tree node, still make a decision
    Tree DecisionTree(Instances examples, Instances parent){

        if(examples.numInstances() == 0){
        	//This has to be transformed into a end node
            return PluralityValue(parent);
        }

        //If there is only 1 attribute, the it is the decision attribute. Makes no sense to do anything else
        if(examples.numAttributes() == 1){
        	//This has to be transformed into a end node
            return PluralityValue(examples);
        }

        double[] decisionValues = examples.attributeToDoubleArray(examples.numAttributes() - 1);
        for(int i = 0; i < examples.numInstances() - 2 ;i++) {
            if (decisionValues[i] != decisionValues[i++]){
            	break;
            }

            if (i == examples.numInstances()){
            	//This has to be transformed into a end node. The index is 0 but could be anything
                return decisionValues[0];
            }
        }
        
        
        Attribute chosenAttribute = Importance(examples);

        double[] attributeExampleValues = examples.attributeToDoubleArray(chosenAttribute.index());
    	
        for (int i = 0; i < chosenAttribute.numValues(); i++) {
            Instances exs = new Instances(examples);

            for (int j = 0; i < attributeExampleValues.length; j++) {
                if (i != (int) attributeExampleValues[j]) {
                    exs.delete(j);
                }
            }
            
            //Make the subsequent branch of the tree
            right.DecisionTree(exs.deleteAttributeAt(chosenAttribute.index()), examples);
        }
    }

    /*function DECISION-TREE-LEARNING(examples,attributes,parent examples)
    returns a tree
    if examples is empty then return PLURALITY-VALUE(parent examples)
        else if all examples have the same classiﬁcation then return the classiﬁcation
            else if attributes is empty then return PLURALITY-VALUE(examples)
            else
                A←argmaxa ∈ attributes IMPORTANCE(a,examples)
                tree ←a new decision tree with root test A
                foreach value vk of A do
                    exs ←{e : e∈examples and e.A = vk}
                    subtree ←DECISION-TREE-LEARNING(exs,attributes −A,examples)
                    add a branch to tree with label (A = vk) and subtree subtree
    return tree
    */
    private double PluralityValue(Instances examples){
    	double maxProbability = 0, probability = 0;
    	boolean tie = false;
    	ArrayList<Integer> decisionIndexes = new ArrayList<Integer>();
    	
    	for(int i = 0; i < examples.attribute(examples.numAttributes()-1).numValues(); i++){
    		probability = ProbabilityDecision(examples,i);
    		
    		if(maxProbability < probability){
    			maxProbability = probability;
    			decisionIndexes.clear();
    			decisionIndexes.add(i);
    			tie = false;
    		} 
    		else { 
    			if(maxProbability == probability){
    				decisionIndexes.add(i);
    				tie = true;
    			}
    		}
    		
    	}
    	
    	if(tie){
    		Random random = new Random();
    		return random.nextInt(decisionIndexes.size() + 1);
    	}
    	else{
    		return decisionIndexes.get(0);
    	}
    }
    
    
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
    	int bestAttributeIndex = 0;
    	
    	for(int i = 0; i < (int) examples.numAttributes() - 1; i++){
    		double gain = entropy -  Remainder(examples,examples.attribute(i));
    		if(maxGain < gain){
    			maxGain = gain;
    			bestAttributeIndex = 1;
    		}
    	}
    	 
    	return examples.attribute(bestAttributeIndex);
    }



}