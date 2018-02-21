package src*;

public class Node {

    private int value;
    private Atribute x;

    private Node father;
    private Node left;
    private Node right;

    public Node(int value, Atribute x) {
        this.value = value;
        this.x = x;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void setX(Atribute x){this.x = x;}

    public int getValue() {
        return value;
    }

    public void getX(Atribute x){return x;}

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}