public class HNode {
    private int id, key;
    private Edge e;
    private HNode left, right, crossEdge, outheap;

    private static int counter = 0;

    public HNode(int key, Edge e) {
        this.id = counter;
        this.key = key;
        this.e = e;
        this.left = null;
        this.right = null;
        this.crossEdge = null;
        this.outheap = null;

        counter++;
    }

    public HNode(int key, Edge e, HNode left, HNode right, HNode crossEdge, HNode outheap) {
        this.id = counter;
        this.key = key;
        this.e = e;
        this.left = left;
        this.right = right;
        this.crossEdge = crossEdge;
        this.outheap = outheap;

        counter++;
    }

    public HNode(int key, Edge e, HNode left, HNode right) {
        this.id = counter;
        this.key = key;
        this.e = e;
        this.left = left;
        this.right = right;
        this.crossEdge = null;
        this.outheap = null;

        counter++;
    }

    public int getId() {
        return this.id;
    }

    public int getKey() {
        return this.key;
    }

    public Edge getEdge() {
        return this.e;
    }

    public HNode getLeft() {
        return this.left;
    }

    public HNode getRight() {
        return this.right;
    }

    public HNode getCrossEdge() {
        return this.crossEdge;
    }

    public HNode getOutheap() {
        return this.outheap;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setEdge(Edge newEdge) {
        this.e = newEdge;
    }

    public void setOutheap(HNode newNode) {
        this.outheap = newNode;
    }

    public void setCrossEdge(HNode newNode) {
        this.crossEdge = newNode;
    }

    public void setLeft(HNode newNode) {
        this.left = newNode;
    }

    public void setRight(HNode newNode) {
        this.right = newNode;
    }

    public int hashCode() {
        return this.id;
    }

    public static void dfs(PersistenceHeaps heap, HNode start) {
        try {
            start.setCrossEdge(heap.getHeap(start.getEdge().getDest()).getRoot());
            dfs(heap, start.getLeft());
            dfs(heap, start.getRight());
            dfs(heap, start.getOutheap());
        } catch (NullPointerException e) {
            return;
        }
    }
}
