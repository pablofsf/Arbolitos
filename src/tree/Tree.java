public class Tree {

    private Node root;


    public Tree( int value ) {
        this.root = new Node( value );
    }

    public Tree( Node root ) {
        this.root = root;
    }

    /* Setters y Getters */
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }



    private void private void addNode( Node node, Node root ) {
        if ( root == null ) {

            this.setRoot(node);
        }
        else {
            if ( node.getValue() <= root.getValue() ) {

                if (root.getLeft() == null) {
                    root.setRight(node);
                }
                else {
                    addNode( node , root.getLeft() );
                }
            }
            else {

                if (root.getRight() == null) {
                    root.setLeft(node);
                }
                else {
                    addNode( node, root.getRight() );
                }
            }
        }
    }

    public void addNode( Node node ) {
        this.addNode( node , this.root );
    }
}