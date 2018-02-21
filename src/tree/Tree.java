package src.tree;

*;

/*
public class Tree {

    private Node root;


    public Tree(int value) {
        this.root = new Node(value, Atribute x);
    }

    public Tree(Node root) {
        this.root = root;
    }

    /* Setters y Getters */
/*
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }


    private void addNode(Node node, Node root) {
        if (root == null) {

            this.setRoot(node);
        } else {
            if (node.getValue() <= root.getValue()) {

                if (root.getLeft() == null) {
                    root.setRight(node);
                } else {
                    addNode(node, root.getLeft());
                }
            } else {

                if (root.getRight() == null) {
                    root.setLeft(node);
                } else {
                    addNode(node, root.getRight());
                }
            }
        }
    }

    private void addNode(Node node, Node root) {
        if (root == null) {
            this.setRoot(node);
        } else {
            if (node.getValue() <= root.getValue()) {

                if (root.getLeft() == null) {
                    root.setRight(node);
                } else {
                    addNode(node, root.getLeft());
                }
            } else {

                if (root.getRight() == null) {
                    root.setLeft(node);
                } else {
                    addNode(node, root.getRight());
                }
            }
        }
    }

    public void addNode(Node node) {
        this.addNode(node, this.root);
    }
}
*/
public class Tree<DataProcessor> {
    private Node<DataProcessor> root;

    public Tree(DataProcessor rootData) {
        root = new Node<Data Processor > ();
        root.data = rootData;
        root.children = new ArrayList<Node<DataProcessor>>();
    }

    public static class Node<DataProcessor> {
        private DataProcessor data;
        private Node<DataProcessor> parent;
        private List<Node<DataProcessor>> children;
    }
}