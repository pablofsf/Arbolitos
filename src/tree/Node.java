package tree;
import data.Attribute;
import java.util.ArrayList;


public class Node {

    private String value;
    private Attribute attribute;

    private Node father;
    public ArrayList<Node> children = new ArrayList<Node>();


    public Node(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;

    }

    public Node(Attribute attribute) {
        this.attribute = attribute;
    }

    public static Node newNode(Attribute attribute) {
        return new Node(attribute);
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

    public static Node newLeafNode(String value) {
        return new Node(null, value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Attribute getAtribute() {
        return this.attribute;
    }

    public String getValue() {
        return value;
    }


}
