import java.util.ArrayList;

public class BH {
    private HNode root;
    private int size;

    public BH() {
        this.root = null;
        this.size = 0;
    }

    private BH(HNode root, int size) {
        this.root = root;
        this.size = size;
    }

    public void insert(int key, Edge element) {
        HNode newNode = new HNode(key, element);
        if (this.root == null) {
            this.root = newNode;
            this.size++;
            return;
        }

        Character[] moves = calculateMoves(this.size);

        HNode parent = this.root;

        for (int i = moves.length - 1; i > 0; i--) {

            if (parent.getKey() > newNode.getKey()) {
                HNode temp = new HNode(newNode.getKey(), newNode.getEdge());
                newNode.setKey(parent.getKey());
                newNode.setEdge(parent.getEdge());
                parent.setKey(temp.getKey());
                parent.setEdge(temp.getEdge());
            }

            if (moves[i] == 'R') {
                parent = parent.getRight();
            } else {
                parent = parent.getLeft();
            }
        }

        if (moves[0] == 'R') {
            parent.setRight(newNode);
        } else {
            parent.setLeft(newNode);
        }

        this.size++;

    }

    public BH insertWithCopies(int key, Edge element) {
        HNode newNode = new HNode(key, element);
        if (this.root == null) {
            return new BH(newNode, this.size + 1);
        }

        Character[] moves = calculateMoves(this.size);
        HNode newRoot = new HNode(this.root.getKey(), this.root.getEdge(), this.root.getLeft(), this.root.getRight());
        HNode parent = newRoot;

        /**
         * Gotta check that the node we are looking at has the correct key to maintain
         * heap order and place them in right order. Issue arises from the fact that we
         * do not have parent pointers and therefore cannot do the bubble up technique.
         */
        for (int i = moves.length - 1; i >= 0; i--) {

            if (parent.getKey() > newNode.getKey()) {
                HNode temp = new HNode(newNode.getKey(), newNode.getEdge());
                newNode.setKey(parent.getKey());
                newNode.setEdge(parent.getEdge());
                parent.setKey(temp.getKey());
                parent.setEdge(temp.getEdge());
            }

            if (i != 0) {
                HNode newChild;
                if (moves[i] == 'R') {
                    newChild = parent.getRight();
                    parent.setRight(
                            new HNode(newChild.getKey(), newChild.getEdge(), newChild.getLeft(), newChild.getRight()));
                    parent = parent.getRight();
                } else {
                    newChild = parent.getLeft();
                    parent.setLeft(
                            new HNode(newChild.getKey(), newChild.getEdge(), newChild.getLeft(), newChild.getRight()));
                    parent = parent.getLeft();
                }
            }
        }

        if (moves[0] == 'R') {
            parent.setRight(newNode);
        } else {
            parent.setLeft(newNode);
        }

        return new BH(newRoot, this.size + 1);

    }

    public BH insertWithCopies(HNode newNode) {
        if (this.root == null) {
            return new BH(newNode, this.size + 1);
        }

        Character[] moves = calculateMoves(this.size);
        HNode newRoot = new HNode(this.root.getKey(), this.root.getEdge(), this.root.getLeft(), this.root.getRight(),
                null, this.root.getOutheap());
        HNode parent = newRoot;

        for (int i = moves.length - 1; i >= 0; i--) {

            if (parent.getKey() > newNode.getKey()) {
                HNode temp = new HNode(newNode.getKey(), newNode.getEdge(), null, null, null, newNode.getOutheap());
                newNode.setKey(parent.getKey());
                newNode.setEdge(parent.getEdge());
                newNode.setOutheap(parent.getOutheap());
                parent.setKey(temp.getKey());
                parent.setEdge(temp.getEdge());
                parent.setOutheap(temp.getOutheap());
            }

            if (i != 0) {
                HNode newChild;
                if (moves[i] == 'R') {
                    newChild = parent.getRight();
                    parent.setRight(new HNode(newChild.getKey(), newChild.getEdge(), newChild.getLeft(),
                            newChild.getRight(), null, newChild.getOutheap()));
                    parent = parent.getRight();
                } else {
                    newChild = parent.getLeft();
                    parent.setLeft(new HNode(newChild.getKey(), newChild.getEdge(), newChild.getLeft(),
                            newChild.getRight(), null, newChild.getOutheap()));
                    parent = parent.getLeft();
                }
            }
        }

        if (moves[0] == 'R') {
            parent.setRight(newNode);
        } else {
            parent.setLeft(newNode);
        }

        return new BH(newRoot, this.size + 1);

    }

    /**
     * Helper function that returns a char[] that tells us what left-right movements
     * we need to make to the insert spot. The movements are calculated from the
     * bottom and up, so to get the right movements from the root, the array must be
     * read backwards
     * 
     * @param endIndex
     * @return
     */
    private static Character[] calculateMoves(int endIndex) {
        ArrayList<Character> moves = new ArrayList<>();

        while (endIndex != 0) {
            if (endIndex % 2 == 0) {
                moves.add('R');
            } else {
                moves.add('L');
            }
            endIndex = parent(endIndex);
        }

        return moves.toArray(new Character[moves.size()]);
    }

    /**
     * Computes and returns the parent index of the given index
     * 
     * @param index
     * @return
     */
    public static int parent(int index) {
        return (int) Math.ceil((double) index / 2) - 1;
    }

    public HNode getRoot() {
        return this.root;
    }

    public String toString() {
        if (this.root != null) {
            return "ROOT: " + Integer.toString(this.root.getKey()) + ", SIZE: " + this.size;
        }
        return "Empty heap";
    }
}