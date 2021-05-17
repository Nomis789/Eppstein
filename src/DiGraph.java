import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DiGraph implements Iterable<Vertex> {
    HashMap<Integer, Vertex> vertices;

    /**
     * Generates a new empty directed graph
     */
    public DiGraph() {
        this.vertices = new HashMap<>();
    }

    /**
     * Returns an iterator for the vertices of this directed graph
     */
    public Iterator<Vertex> iterator() {
        return this.vertices.values().iterator();
    }

    /**
     * Adds a new vertex to the directed graph. Use this when generating a graph
     * with no correlation to other graphs
     */
    public void addVertex() {
        this.vertices.put(this.size(), new Vertex(vertices.size()));
    }

    /**
     * Adds a new customized vertex. Use this when creating new graphs based on
     * existing graphs
     * 
     * @param v Vertex to be added
     */
    public void addVertex(Vertex v) {
        this.vertices.put(v.getId(), v);
    }

    /**
     * Returns the vertex associated with the id. If no vertex with the specified id
     * exists, return null
     * 
     * @param id id to retrieve vertex
     * @return The Vertex associated with the id.
     */
    public Vertex getVertex(int id) {
        return this.vertices.get(id);
    }

    public void removeEdge(Edge e) {
        e.getSrc().removeEdge(e);
    }

    public void clearAllEdges() {
        for (Vertex v : this) {
            v.clearEdges();
        }
    }

    public void deleteVertex(int id) {
        Vertex v = getVertex(id);
        if (v != null) {
            vertices.remove(id);
        }
    }

    public void addEdge(Vertex src, Vertex dest, int weight) {
        src.addEdge(dest, weight);
    }

    public void reverse() {
        boolean initiallyReversed = isReversed();
        for (Vertex v : this) {
            for (Iterator<Edge> iterator = v.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();
                if (e.getSrc().equals(e.getDest())) {
                    continue;
                }
                if (e.isReversed() == initiallyReversed) {
                    iterator.remove();
                    e.reverse();
                    Vertex newSource = e.getSrc();
                    newSource.addEdge(e);
                }
            }
        }
    }

    private boolean isReversed() {
        for (Vertex vertex : this) {
            Edge[] edges = vertex.getEdges();
            if (edges.length > 0) {
                return edges[0].isReversed();
            }
        }
        return false;
    }

    public String toString() {
        String s = "";
        for (Vertex vertex : this) {
            s += vertex + "\n";
        }
        return s;
    }

    public DiGraph copy() {
        DiGraph copy = new DiGraph();
        for (Vertex vertex : this) {
            copy.addVertex(new Vertex(vertex.getId()));
        }
        for (Vertex vertex : copy) {
            for (Edge e : this.getVertex(vertex.getId())) {
                copy.addEdge(vertex, copy.getVertex(e.getDest().getId()), e.getWeight());
            }
        }
        return copy;
    }

    protected static DiGraph buildFromMap(HashMap<Vertex, Vertex> mapping) {
        DiGraph result = new DiGraph();

        for (Vertex v : mapping.keySet()) {
            // if (v.getWeight() != Integer.MAX_VALUE) {
            result.addVertex(v);
            // }
        }
        result.clearAllEdges();
        for (Vertex v : mapping.keySet()) {
            Vertex prev = mapping.get(v);
            if (prev != null) {
                int weight = v.getWeight() - prev.getWeight();
                result.addEdge(prev, v, weight);
            }
        }

        return result;
    }

    public int size() {
        return this.vertices.size();
    }

    /**
     * Returns a new graph of dg1, where all the edges that also are in dg2 are
     * removed. If the two graph dop not have the same vertices (Every vertex' id in
     * dg1 are contained in dg2), then an empty DiGraph will be returned
     * 
     * @param dg1 The first DiGraph
     * @param dg2
     * @return New DiGraph with all the edges of dg2 removed from dg1.
     */
    public DiGraph difference(DiGraph dg2) {
        DiGraph diff = this.copy();
        if (haveSameVertices(dg2)) {
            for (Vertex v : dg2) {
                ArrayList<Edge> toRemove = new ArrayList<>();
                for (Edge edge : v) {
                    if (getVertex(v.getId()).containsEdge(edge)) {
                        toRemove.add(edge);
                    }
                }
                System.out.println(toRemove);
                diff.getVertex(v.getId()).removeAll(toRemove);
            }
        }
        return diff;
    }

    /**
     * Returns a digraph, with edges modified to signal sidetrack edges. Computes
     * sidetrack edges (Edges that are not in the shortest path tree) according to
     * the given shortest path tree. Sidetrack edges tells us how much our shortest
     * path is derailed by going through such an edge and then otherwise follow the
     * edges to the shortest path tree. Edges that have weight = 0, are edges that
     * are contained in the shortest path tree, and are therefore not part of the
     * graph. Original graph is not destroyed.
     * 
     * @param spt Shortest path tree, which we compute the sidetrack edges.
     * @return A new graph with sidetrack costs on the edges.
     */
    public DiGraph computeSidetracks(DiGraph spt) {
        DiGraph diff = this.copy();
        if (haveSameVertices(spt)) {
            for (Vertex v : diff) {
                ArrayList<Edge> toRemove = new ArrayList<>();
                for (Edge edge : v) {
                    int headDistance = spt.getVertex(edge.getDest().getId()).getWeight();
                    int tailDistance = spt.getVertex(edge.getSrc().getId()).getWeight();
                    int weight = edge.getWeight() + headDistance - tailDistance;
                    if (headDistance == Integer.MAX_VALUE || tailDistance == Integer.MAX_VALUE || weight == 0) {
                        toRemove.add(edge);
                    } else {
                        edge.setWeight(weight);
                    }
                }
                v.removeAll(toRemove);
            }
        }
        return diff;
    }

    /**
     * Helper function to determine if this graph has the same vertices as the
     * parameter graph. Will return if all vertex id's in this graph are contained
     * in the parameter graph
     * 
     * @param graph Graph to compare vertices with
     * @return True if and only if all the vertex id's are identical in the
     *         parameter graph
     */
    private boolean haveSameVertices(DiGraph graph) {
        if (this.size() != graph.size()) {
            return false;
        }
        for (Vertex vertex : this) {
            if (graph.getVertex(vertex.getId()) == null) {
                return false;
            }
        }
        return true;
    }

}
