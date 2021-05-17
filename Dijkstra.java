import java.util.HashMap;

public class Dijkstra extends DiGraph {

    /**
     * Returns a single source shortest path tree from the specified source vertex.
     * Vertices with weights equal to Integer.MAX_VALUE are vertices that are
     * unreachable from the source vertex. Doesn't alter the input graph
     * 
     * @param src   Source vertex from which the shortest path tree is rooted at
     * @param graph The graph from which the shortest path is found rooted at the
     *              source
     * @return Single-source shortest path tree
     */
    public static DiGraph shortestPathTree(Vertex src, DiGraph graph) {
        HashMap<Vertex, Vertex> mapping = new HashMap<>();
        DiGraph copy = graph.copy();
        Vertex srcCopy = copy.getVertex(src.getId());
        mapping.put(srcCopy, null);
        QuakeHeap<Vertex> pq = new QuakeHeap<>();
        for (Vertex v : copy) {
            if (v != srcCopy) {
                v.setWeight(Integer.MAX_VALUE);
                mapping.put(v, null);
            }
            pq.insert(v.getWeight(), v);
        }

        while (!pq.isEmpty()) {
            Vertex u = pq.deleteMin();
            if (u == null) {
                break;
            }
            for (Edge e : u) {
                Vertex neighbourVertex = e.getDest();
                int alt = u.getWeight() + e.getWeight();
                if (neighbourVertex.getWeight() > alt) {
                    mapping.replace(neighbourVertex, u);
                    neighbourVertex.setWeight(alt);
                    pq.decreaseKey(neighbourVertex, alt);
                }
            }
        }

        return buildFromMap(mapping);

    }
}
