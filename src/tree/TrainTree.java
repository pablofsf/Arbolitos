package tree;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import data.DataProcessor;
import data.Attribute;
import tree.Node;

public class TrainTree {


    public Node root;


    public TrainTree() {

    }

    public Node train(DataProcessor examples, DataProcessor parent) {
        return root = DecisionTree(examples, parent, null);
    }
    
    public Node DecisionTree(DataProcessor examples, DataProcessor parent, Node root) {
    	int numAttributes = examples.numAttributes();
    	
        if (examples.numExamples() == 0) {
            return Node.newLeafNode(examples.getAttribute(numAttributes-1).getValues().get(PluralityValue(parent)));
        }
        else {
        	if(examples.numExamples() == 1){
        		return Node.newLeafNode(examples.getAttribute(numAttributes-1).getValues().get(examples.attributeToArray(numAttributes - 1)[0]));
        	}
        }

        if (examples.numAttributes() == 1) {
            return Node.newLeafNode(examples.getAttribute(numAttributes-1).getValues().get(PluralityValue(examples)));
        }

        int[] decisionValues = examples.attributeToArray(examples.numAttributes() - 1);
        for (int i = 0; i < examples.numExamples() - 1;i++) {
            if (decisionValues[i] != decisionValues[i+1]) {
                break;
            }

            if (i == examples.numExamples() - 2) {
                return Node.newLeafNode(examples.getAttribute(numAttributes-1).getValues().get(decisionValues[i]));
            }
        }


        Attribute chosenAttribute = Importance(examples);

        int[] attributeExampleValues = examples.attributeToArray(chosenAttribute);
        Node node = new Node(chosenAttribute);
        for (int attVal = 0; attVal < chosenAttribute.numValues(); attVal++) {
            DataProcessor exs = new DataProcessor(examples);

            //Decreasing counter. As the array in dynamic, if it is increasing we screw it up
            for (int exValIndex = attributeExampleValues.length - 1; exValIndex >= 0; exValIndex--) {
                if (attVal != attributeExampleValues[exValIndex]) {
                    exs.deleteExample(exValIndex);
                }
            }
            exs.deleteAttribute(chosenAttribute);
            node.addChild(DecisionTree(exs, examples, node));

        }
        return node;
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
    private int PluralityValue(DataProcessor examples) {
        double maxProbability = 0, probability = 0;
        boolean tie = false;
        ArrayList<Integer> decisionIndexes = new ArrayList<Integer>();

        for (int i = 0; i < examples.getAttribute(examples.numAttributes() - 1).numValues(); i++) {
            probability = ProbabilityDecision(examples, i);

            if (maxProbability < probability) {
                maxProbability = probability;
                decisionIndexes.clear();
                decisionIndexes.add(i);
                tie = false;
            } else {
                if (maxProbability == probability) {
                    decisionIndexes.add(i);
                    tie = true;
                }
            }

        }

        if (tie) {
            Random random = new Random();
            return (int) random.nextInt(decisionIndexes.size());
        } else {
            return (int) decisionIndexes.get(0);
        }
    }


    private static double log2(double val) {
        return Math.log(val) / Math.log(2);
    }

    //Taken a certain group of data, where several decisions can be made, check the probability of a certain one
    private double ProbabilityDecision(DataProcessor examples, int decision) {
        int decisionsTrue = 0;

        int[] decisionsArray = examples.attributeToArray(examples.numAttributes() - 1);
        for (int i = 0; i < examples.numExamples(); i++) {
            if (decision == decisionsArray[i]) {
                decisionsTrue++;
            }
        }

        if(examples.numExamples() == 0)
        	return 0;
        else
        	return ((double) decisionsTrue) / ((double) examples.numExamples());
    }

    //Given a certain data, compute its entropy
    private double Entropy(DataProcessor examples) {
        double entropy = 0;
        double probability;

        for (int i = 0; i < examples.getAttribute(examples.numAttributes() - 1).numValues(); i++) {
            probability = ProbabilityDecision(examples, i);
            if (probability != 0)
                entropy -= probability * log2(probability);
        }

        return entropy;
    }

    //Given a certain data, the remainder is calculated this way:
    //Split the data into sets according to the different values of the attribute you are given
    //For each set, calculate its probability and multiply per the entropy of that set. Add all of them
    private double Remainder(DataProcessor examples, Attribute attribute) {
        double remainder = 0;

        int[] attributeExampleValues = examples.attributeToArray(attribute);
        for (int attVal = 0; attVal < attribute.numValues(); attVal++) {

            DataProcessor reducedExamples = new DataProcessor(examples);

            //Decreasing counter. As the array in dynamic, if it is increasing we screw it up
            for (int exValIndex = attributeExampleValues.length - 1; exValIndex >= 0; exValIndex--) {
                if (attributeExampleValues[exValIndex] != attVal) {
                    reducedExamples.deleteExample(exValIndex);
                }
            }
            if(examples.numExamples() != 0)
            	remainder += ((double) reducedExamples.numExamples()) / ((double) examples.numExamples()) * Entropy(reducedExamples);
        }

        return remainder;
    }

    private Attribute Importance(DataProcessor examples) {
        double maxGain = 0, entropy = Entropy(examples);
        int bestAttributeIndex = 0;

        for (int i = 0; i < examples.numAttributes() - 1; i++) {
            double gain = entropy - Remainder(examples, examples.getAttribute(i));
            if (maxGain < gain) {
                maxGain = gain;
                bestAttributeIndex = i;
            }
        }

        return examples.getAttribute(bestAttributeIndex);
    }

    public void print(){
    	printTree(this.root,0);
    }
    
    private void printTree(Node tree, int depth){
		Attribute attribute = tree.getAtribute();
		
		if(attribute == null){
			System.out.print(": " + tree.getValue() + "\n");
			return;
		}
		else{
			if(depth != 0){
				System.out.print("\n");
			}
		}
		
		ArrayList<String> attributeValues = attribute.getValues();
		ArrayList<Node> children = tree.getChildren();
		
		for(int i = 0; i < attribute.numValues(); i++){
			for(int j = 0; j < depth; j++){
				System.out.print("  ");
			}
			System.out.print(attribute.getName() + " = " + attributeValues.get(i));
			printTree(children.get(i),depth + 1);
		}
	}

	public static void main(String args[]) throws FileNotFoundException {

        DataProcessor data = new DataProcessor("src/data/wait.arff");
        TrainTree tree = new TrainTree();
        tree.train(data,data);
        tree.print();
    }

}
