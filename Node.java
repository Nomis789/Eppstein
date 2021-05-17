public class Node<E> {
    private int level;
    private Leaf<E> leaf;
    private Node<E> parent, left, right;

    public Node(int level, Leaf<E> leaf, Node<E> parent, Node<E> left, Node<E> right) {
        this.level = level;
        this.leaf = leaf;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public Node(Leaf<E> leaf) {
        this.level = 0;
        this.leaf = leaf;
        this.parent = this.left = this.right = null;
    }

    public int getLevel() {
        return this.level;
    }

    public Leaf<E> getLeaf() {
        return this.leaf;
    }

    public Node<E> getParent() {
        return this.parent;
    }

    public Node<E> getLeft() {
        return this.left;
    }

    public Node<E> getRight() {
        return this.right;
    }

    public void setElement(Leaf<E> newValue) {
        this.leaf = newValue;
    }

    public void setParent(Node<E> newParent) {
        this.parent = newParent;
    }

    public void setLeft(Node<E> newLeft) {
        this.left = newLeft;
    }

    public void setRight(Node<E> newRight) {
        this.right = newRight;
    }

    public String toString() {
        return Integer.toString(this.leaf.getKey());
    }
}
