package tree;

import weka.core.Instances;
import weka.core.Attribute;
import weka.core.Instance;



public class TrainTree{

    Tree DecisionTree(Instances examples, Attribute attributes,Instances parent){

        if(examples.nummInstances() == 0){
            return ProbabilityDecision(parent);//Arreglar
        }

        if(attributes == NULL){
            return ProbabilityDecision(examples);//Arreglar
        }

        for(int i = 0; i < examples.numInstances();i++) {
            if (examples.attributeToDoubleArray(examples.numAtributes() - 1)[i] == examples.attributeToDoubleArray(examples.numAtributes() - 1)[i++];){

            }else{break;}

            if (i == examples.numInstances() - 1){

                return examples.attributeToDoubleArray(examples.numAtributes() - 1);
            }
        }

        Instances exs = new Instances;
        for (int i = 0; i < attributes.numValues(); i++) {
            for (int j = 0; i < examples.numInstances(); j++) {
                if (i = examples.attributesToDoubleArray(attributes.index())[j]) {
                    exs.add(examples.instance(j));
                    right.DecisionTree(exs, atrributes - A, examples);
                }
            }
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



}