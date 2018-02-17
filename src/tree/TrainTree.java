package tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import data.*;


public class TrainTree{
	
	public TrainTree(){
		
	}

	//For the 3 comments that say "//This has to be transformed into a end node", the result returned is an integer
	//that represents that represents a value for the attribute we are looking for.
	//In the example wait.arff, the attribute that we are looking for is @attribute wait {yes,no}
	//Then, returning 0 means wait = yes and returning 1 means wait = no
	//Both the number or the string could be stored in the tree node, still make a decision
    Tree DecisionTree(DataProcessor examples, DataProcessor parent){

        if(examples.numExamples() == 0){
        	//This has to be transformed into a end node
            return PluralityValue(parent);
        }

        //If there is only 1 attribute, the it is the decision attribute. Makes no sense to do anything else
        if(examples.numAttributes() == 1){
        	//This has to be transformed into a end node
            return PluralityValue(examples);
        }

        int[] decisionValues = examples.attributeToArray(examples.numAttributes() - 1);
        for(int i = 0; i < examples.numExamples() - 2 ;i++) {
            if (decisionValues[i] != decisionValues[i++]){
            	break;
            }

            if (i == examples.numExamples()){
            	//This has to be transformed into a end node. The index is 0 but could be anything
                return decisionValues[0];
            }
        }
        
        
        Attribute chosenAttribute = Importance(examples);

        int[] attributeExampleValues = examples.attributeToArray(chosenAttribute);
    	
        for (int i = 0; i < chosenAttribute.numValues(); i++) {
            DataProcessor exs = new DataProcessor(examples);

            for (int j = 0; i < attributeExampleValues.length; j++) {
                if (i != (int) attributeExampleValues[j]) {
                    exs.deleteExample(j);
                }
            }
            
            //Make the subsequent branch of the tree
            right.DecisionTree(exs.deleteAttribute(chosenAttribute), examples);
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
    private double PluralityValue(DataProcessor examples){
    	double maxProbability = 0, probability = 0;
    	boolean tie = false;
    	ArrayList<Integer> decisionIndexes = new ArrayList<Integer>();
    	
    	for(int i = 0; i < examples.getAttribute(examples.numAttributes()-1).numValues(); i++){
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
    private double ProbabilityDecision(DataProcessor examples,int decision){
    	int decisionsTrue = 0;
    	
    	int[] decisionsArray = examples.attributeToArray(examples.numAttributes()-1);
    	for(int i = 0; i < examples.numExamples(); i++){
    		if(decision == (int) decisionsArray[i]){
    			decisionsTrue++;
    		}
    	}
    	
    	return ((double) decisionsTrue)/((double)examples.numExamples());
    }
    
    //Given a certain data, compute its entropy
    private double Entropy(DataProcessor examples){
    	double entropy = 0;
    	double probability;
    	
    	for(int i = 0; i < examples.getAttribute(examples.numAttributes()-1).numValues(); i++){
    		probability = ProbabilityDecision(examples,i);
    		if(probability != 0)
    			entropy -= probability*log2(probability);
    	}
    	
    	return entropy;
    }
    
    //Given a certain data, the remainder is calculated this way:
    //Split the data into sets according to the different values of the attribute you are given
    //For each set, calculate its probability and multiply per the entropy of that set. Add all of them
    private double Remainder(DataProcessor examples, Attribute attribute){
    	double remainder = 0;
    	
    	int[] attributeExampleValues = examples.attributeToArray(attribute);
    	for(int i = 0; i < (int) attribute.numValues(); i++){
        	
    		//This loop and variable could be placed into a function, together with the double[]
    		DataProcessor reducedExamples = new DataProcessor(examples);

    		//Decreasing counter. As the array in dynamic, if it is increasing we screw it up
    		for(int j = attributeExampleValues.length - 1; j >= 0; j--){
    			if((int) attributeExampleValues[j] != i){
    				reducedExamples.deleteExample(j);
    			}
    		}
    		
    		remainder += (reducedExamples.numExamples()/examples.numExamples())*Entropy(reducedExamples);
    	}
    	
    	return remainder;
    }
    
    private Attribute Importance(DataProcessor examples){
    	double maxGain = 0, entropy = Entropy(examples);
    	int bestAttributeIndex = 0;
    	
    	for(int i = 0; i < (int) examples.numAttributes() - 1; i++){
    		double gain = entropy -  Remainder(examples,examples.getAttribute(i));
    		if(maxGain < gain){
    			maxGain = gain;
    			bestAttributeIndex = i;
    		}
    	}
    	 
    	return examples.getAttribute(bestAttributeIndex);
    }

    public static void main(String args[]) throws FileNotFoundException{
	
    @SuppressWarnings("unused")
	DataProcessor data = new DataProcessor("src/data/weather.nominal.arff");
    TrainTree tree = new TrainTree();
	tree.Importance(data);
	}

}