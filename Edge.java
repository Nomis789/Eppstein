public class Edge {
    private Vertex src;
    private Vertex dest;
    private int weight;
    private boolean reversed;

    public Edge(Vertex src, Vertex dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.reversed = false;
    }

    public Edge(int id, Vertex src, Vertex dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.reversed = false;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Vertex getSrc() {
        return this.src;
    }

    public Vertex getDest() {
        return this.dest;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isReversed() {
        return this.reversed;
    }

    public void reverse() {
        this.reversed = this.reversed ? false : true;
        Vertex temp = this.src;
        this.src = this.dest;
        this.dest = temp;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) o;
        return this.src.getId() == e.src.getId() && this.dest.getId() == e.dest.getId() && this.weight == e.weight;
    }

    public String toString() {
        return "(src: " + this.src.getId() + ", dest: " + this.dest.getId() + ", weight: " + this.weight + ")";
    }
}
