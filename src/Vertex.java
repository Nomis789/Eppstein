import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Vertex implements Iterable<Edge> {
    private int id;
    private int weight;
    private ArrayList<Edge> edges;

    public Vertex(int id, int weight, Edge[] edges) {
        this.id = id;
        this.weight = weight;
        this.edges = new ArrayList<>();
        for (Edge edge : edges) {
            this.edges.add(edge);
        }
    }

    public Iterator<Edge> iterator() {
        return this.edges.iterator();
    }

    public Vertex(int id, int weight, ArrayList<Edge> edges) {
        this.id = id;
        this.weight = weight;
        this.edges = new ArrayList<>();
        for (Edge edge : edges) {
            this.edges.add(edge);
        }
    }

    public Vertex(int id) {
        this.id = id;
        this.weight = 0;
        this.edges = new ArrayList<>();
    }

    public Vertex(int id, int weight) {
        this.id = id;
        this.weight = weight;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Vertex dest, int weight) {
        Edge newEdge = new Edge(this, dest, weight);
        if (edges.contains(newEdge)) {
            return;
        } else {
            edges.add(new Edge(this, dest, weight));
        }
    }

    public void addEdge(Edge e) {
        if (e.getSrc().id == this.id) {
            edges.add(e);
        }
    }

    public void removeEdge(Edge e) {
        this.edges.remove(e);
    }

    public boolean containsEdge(Edge e) {
        return this.edges.contains(e);
    }

    public void clearEdges() {
        this.edges.clear();
    }

    public void removeAll(Collection<Edge> c) {
        this.edges.removeAll(c);
    }

    public int numOfEdges() {
        return this.edges.size();
    }

    public int getId() {
        return this.id;
    }

    public int getWeight() {
        return this.weight;
    }

    public Edge[] getEdges() {
        return this.edges.toArray(new Edge[this.edges.size()]);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Vertex)) {
            return false;
        }
        Vertex v = (Vertex) o;
        return this.id == v.id;
    }

    public int hashCode() {
        return this.id;
    }

    public Vertex copy() {
        Edge[] copyEdges = new Edge[this.edges.size()];
        for (int i = 0; i < this.edges.size(); i++) {
            copyEdges[i] = new Edge(this.edges.get(i).getSrc(), this.edges.get(i).getDest(),
                    this.edges.get(i).getWeight());
        }
        return new Vertex(this.id, this.weight, copyEdges);
    }

    public String toString() {
        String s = "Vertex id: " + this.id + "\n\tWeight: " + this.weight + "\n\tEdges to:";
        for (Edge edge : this.edges) {
            s += "\n\t\t" + edge;
        }

        return s + "\n";
    }

    // public String toString() {
    // return "Vertex id: " + this.id;
    // }
}
