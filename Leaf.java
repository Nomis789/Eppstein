public class Leaf<E> {

    // Indehold det faktiske element og key'en. Alle nodes skal blot referere til
    // dette. Leaf skal desuden også have en reference til det højeste element
    // således man let kan tilgå dette.
    private int key;

    private E value;

    private Node<E> maxNode;

    public Leaf(int key, E value, Node<E> maxNode) {
        this.key = key;
        this.value = value;
        this.maxNode = maxNode;
    }

    public Leaf(int key, E value) {
        this.key = key;
        this.value = value;
        this.maxNode = null;
    }

    public void setKey(int newkey) {
        this.key = newkey;
    }

    public void setMaxNode(Node<E> n) {
        this.maxNode = n;
    }

    public int getKey() {
        return this.key;
    }

    public E getValue() {
        return this.value;
    }

    public Node<E> getMaxNode() {
        return this.maxNode;
    }

    public String toString() {
        return this.value.toString();
    }
}
