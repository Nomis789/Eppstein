import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class QuakeHeap<E> {
    private ArrayList<LinkedList<Node<E>>> roots;

    private ArrayList<Integer> nodesPerLevel;

    private HashMap<Integer, Leaf<E>> leaves;

    public QuakeHeap() {
        this.roots = new ArrayList<>();
        this.roots.add(new LinkedList<>());
        this.leaves = new HashMap<>();
        this.nodesPerLevel = new ArrayList<>();
        this.nodesPerLevel.add(0);
    }

    /**
     * Inserts a new node in the quake heap
     * 
     * @param value Value for the new node
     */
    public void insert(int key, E value) {
        Leaf<E> leaf = new Leaf<>(key, value);
        Node<E> newNode = new Node<E>(leaf);
        leaf.setMaxNode(newNode);
        this.roots.get(0).add(newNode);
        this.leaves.put(value.hashCode(), leaf);
        this.nodesPerLevel.set(0, this.nodesPerLevel.get(0) + 1);
    }

    /**
     * Decreases the key of an element
     * 
     * @param element Element to which the key needs to be decreased
     * @param value   The new value for the node
     */
    public void decreaseKey(E element, int key) {
        Leaf<E> leafElement = this.leaves.get(element.hashCode());
        if (key < leafElement.getKey()) {
            cut(leafElement.getMaxNode());
            leafElement.setKey(key);
        }
    }

    /**
     * Finds and deletes the minimum element in the quake heap and returns it.
     * 
     * @return The minimum element
     */
    public E deleteMin() {
        Node<E> min = findMin();
        if (min == null) {
            return null;
        }
        deleteRecursion(min, min);
        this.leaves.remove(min.getLeaf().getValue().hashCode());
        continousLink();
        quake();
        return min.getLeaf().getValue();
    }

    /**
     * 
     * @param n
     * @param min
     */
    private void deleteRecursion(Node<E> n, Node<E> min) {
        if (n == null) {
            return;
        } else if (n.getRight() != null) {
            cut(n.getRight());
        }
        Node<E> left = n.getLeft();
        n.setLeft(null);
        n.setParent(null);
        nodesPerLevel.set(n.getLevel(), nodesPerLevel.get(n.getLevel()) - 1);
        deleteRecursion(left, min);
    }

    /**
     * Finds the minimum root node in the quake heap and returns it.
     * 
     * @return Minimum root node out of all roots
     */
    private Node<E> findMin() {
        Node<E> min = null;
        int minKey = Integer.MAX_VALUE;
        LinkedList<Node<E>> minList = null;
        for (LinkedList<Node<E>> ll : roots) {
            for (Node<E> node : ll) {
                if (node.getLeaf().getKey() < minKey) {
                    min = node;
                    minKey = node.getLeaf().getKey();
                    minList = ll;
                }
            }
        }
        if (min != null) {
            minList.remove(min);
        }
        return min;
    }

    /**
     * Links together trees in all levels until there are 1 or less trees of each
     * height.
     */
    private void continousLink() {
        for (int i = 0; i < this.roots.size(); i++) {
            LinkedList<Node<E>> trees = this.roots.get(i);
            while (trees.size() > 1) {
                link(trees.get(0), trees.get(1));
            }
        }
    }

    /**
     * Calculates if the invariant is broken at any levels (n_i+1 <= an_i). If so
     * then proceed to delete all nodes that are above that level and create new
     * trees.
     */
    private void quake() {
        int quakeLevel = -1;
        for (int i = 0; i < nodesPerLevel.size() - 1; i++) {
            if (nodesPerLevel.get(i + 1) > Math.ceil(0.75 * nodesPerLevel.get(i))) {
                quakeLevel = i + 1;
                break;
            }
        }
        if (quakeLevel != -1) {
            for (int i = quakeLevel; i < roots.size(); i++) {
                for (Node<E> root : roots.get(i)) {
                    quakeHelper(quakeLevel, root);
                }
            }
            nodesPerLevel.subList(quakeLevel, nodesPerLevel.size()).clear();
            roots.subList(quakeLevel, roots.size()).clear();
        }
    }

    /**
     * Quake helper method. Deletes all nodes from root to minimum level
     * 
     * @param minLevel
     * @param n
     */
    private void quakeHelper(int minLevel, Node<E> n) {
        if (n == null) {
            return;
        }
        Node<E> left = n.getLeft();
        Node<E> right = n.getRight();
        if (n.getLevel() == minLevel) {
            n.getLeaf().setMaxNode(left);
            if (right != null) {
                cut(right);
            }
            left.setParent(null);
            roots.get(left.getLevel()).add(left);
            n.setLeft(null);
        } else {
            quakeHelper(minLevel, left);
            quakeHelper(minLevel, right);
            n.setParent(null);
            n.setLeft(null);
            n.setRight(null);
        }
    }

    private void link(Node<E> t1, Node<E> t2) {
        int height = t1.getLevel();
        Leaf<E> l1 = t1.getLeaf();
        Leaf<E> l2 = t2.getLeaf();
        if (height == t2.getLevel()) {
            Node<E> newRoot;
            if (l1.getKey() <= l2.getKey()) {
                newRoot = new Node<E>(height + 1, l1, null, t1, t2);
                l1.setMaxNode(newRoot);
            } else {
                newRoot = new Node<E>(height + 1, l2, null, t2, t1);
                l2.setMaxNode(newRoot);
            }

            // Removing the this.roots t1 and t2 from the list, since they now have been
            // linked together
            this.roots.get(height).remove(t1);
            this.roots.get(height).remove(t2);

            if (this.roots.size() - 1 == height) {
                this.roots.add(new LinkedList<>());
                this.nodesPerLevel.add(0);
            }
            this.roots.get(height + 1).add(newRoot);
            this.nodesPerLevel.set(height + 1, this.nodesPerLevel.get(height + 1) + 1);

            // Set the correct references
            t1.setParent(newRoot);
            t2.setParent(newRoot);
            newRoot.getLeaf().setMaxNode(newRoot);
        }
    }

    private void cut(Node<E> n) {
        Node<E> parent = n.getParent();
        if (parent != null && n.getLeaf() != parent.getLeaf()) {
            parent.setRight(null);
            n.setParent(null);
            this.roots.get(n.getLevel()).add(n);
        }
    }

    public String toString() {
        String result = "";
        int level = 0;
        for (LinkedList<Node<E>> ll : roots) {
            result += level + ": ";
            for (Node<E> node : ll) {
                result += node.toString() + ", ";
            }
            result += "\n";
            level++;
        }
        return result;
    }

    public boolean isEmpty() {
        return nodesPerLevel.get(0) == 0;
    }
}