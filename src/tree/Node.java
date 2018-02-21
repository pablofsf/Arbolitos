package Arbolitos2.src.tree;
import data.DataProcessor;

import java.util.ArrayList;


public class Node {

    private double value;
    private DataProcessor attribute;

    private Node father;
    public ArrayList<Node> children = new ArrayList<Node>();

    public Node(DataProcessor atributte, double value) {
        this.attribute = attribute;
        this.value = value;

    }

    public Node(DataProcessor attribute) {
        this.attribute = attribute;
    }

    public static Node newNode(DataProcessor atribute) {
        return new Node(atribute);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public static Node newLeafNode(double value) {
        return new Node(null, value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void getAtribute(DataProcessor atribute) {
        this.attribute = atribute;
    }

    public double getValue() {
        return value;
    }


}