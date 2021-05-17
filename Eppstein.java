import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Eppstein {
    public static ArrayList<ArrayList<Edge>> kShortestPaths(Vertex src, Vertex dest, DiGraph graph, int k) {
        // First reverse the graph to generate a shortest path tree from the destination
        // vertex
        graph.reverse();

        // Calculate the shortest path tree with Dijkstras algorithm from destination
        // vertex
        DiGraph spt = Dijkstra.shortestPathTree(dest, graph);

        // Integer.MAX_VALUE corresponds to infinity here, which means that if our
        // source vertex have that weight, no path can be found from source to
        // destination vertex
        if (spt.getVertex(src.getId()).getWeight() == Integer.MAX_VALUE) {
            return new ArrayList<>();
        }

        // Reverse the graph and the shortest path tree to calculate the actual
        // sidetrack costs
        graph.reverse();
        spt.reverse();
        DiGraph sidetracks = graph.computeSidetracks(spt);
        spt.reverse();
        PersistenceHeaps heap = new PersistenceHeaps();

        // Generate the outheaps and the heaps that are ordered after how much an edge
        // sidetracks and can be chosen from the various vertices
        generateOutHeaps(sidetracks, heap, new BH(), spt.getVertex(dest.getId()));

        // Afterward add cross edges, which are edges in our heap structure that goes
        // over to a different vertex' sidetrack edges. Enables us to add more than one
        // sidetrack edge in our k shortest path.
        addCrossEdges(heap);
        spt.reverse();

        // Now we choose the k shortest paths in the implicit representation. It is done
        // through a priority queue.
        ArrayList<ArrayList<Edge>> res = chooseKPaths(k, heap, spt.getVertex(src.getId()));

        // Converts the paths from the implicit representation to complete paths
        ArrayList<ArrayList<Edge>> completePaths = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            if (i == 0) {
                // If i == 0, then it means we just follow the shortest path tree and do not go
                // through any sidetrack edges
                completePaths.add(completePath(spt, spt.getVertex(src.getId()), new ArrayList<>()));
            } else {
                completePaths.add(completePath(spt, spt.getVertex(src.getId()), res.get(i)));
            }
        }
        return completePaths;
    }

    private static ArrayList<Edge> completePath(DiGraph spt, Vertex src, ArrayList<Edge> sidetracks) {
        ArrayList<Edge> result = new ArrayList<>();
        Vertex temp = src;
        Iterator<Edge> iterator = sidetracks.iterator();
        Edge nextSidetrack;
        if (iterator.hasNext()) {
            nextSidetrack = iterator.next();
            boolean hasEnded = false;
            while (temp.numOfEdges() != 0) {
                if (temp.equals(nextSidetrack.getSrc()) && !hasEnded) {
                    result.add(nextSidetrack);
                    temp = spt.getVertex(nextSidetrack.getDest().getId());
                    if (iterator.hasNext()) {
                        nextSidetrack = iterator.next();
                    } else {
                        hasEnded = true;
                    }
                } else {
                    Edge pathEdge = temp.getEdges()[0];
                    result.add(pathEdge);
                    temp = pathEdge.getDest();
                }
            }
        }
        while (temp.numOfEdges() != 0) {
            Edge pathEdge = temp.getEdges()[0];
            result.add(pathEdge);
            temp = pathEdge.getDest();
        }
        return result;
    }

    /**
     * Creates outheaps for all vertices in the shortest path tree, and creates them
     * in a dfs manner, starting at the source of the shortest path tree. The
     * outheaps are merged together creating bigger heaps the further away from the
     * source it gets.
     * 
     * @param sidetracks A digraph, where all the edges are sidetrack edges.
     * @param heap       The set of binary heaps containing outheaps of all
     *                   vertices.
     * @param initial    The initial heap to off with. Must be empty in the start
     * @param v          The start vertex in the shortest path tree (Preferably the
     *                   source).
     */
    private static void generateOutHeaps(DiGraph sidetracks, PersistenceHeaps heap, BH initial, Vertex v) {
        OutHeap outheap = new OutHeap(sidetracks.getVertex(v.getId()));

        BH temp = initial;
        if (outheap.getOutroot() != null) {
            temp = initial.insertWithCopies(outheap.getOutroot());
        }

        heap.put(v, temp);

        for (Edge e : v) {
            generateOutHeaps(sidetracks, heap, temp, e.getDest());
        }
    }

    private static void addCrossEdges(PersistenceHeaps heap) {
        for (BH bh : heap) {
            HNode.dfs(heap, bh.getRoot());
        }
    }

    /**
     * Should return a list of k shortest paths defined by their sidetrack edges.
     * Doesn't work, don't know how to list them through cross edges.
     * 
     * @param k
     * @param heap
     * @param src
     */
    private static ArrayList<ArrayList<Edge>> chooseKPaths(int k, PersistenceHeaps heap, Vertex src) {
        PriorityQueue<EppsteinPath> pq = new PriorityQueue<>();
        ArrayList<EppsteinPath> paths = new ArrayList<>();
        ArrayList<ArrayList<Edge>> explicit = new ArrayList<>();
        HNode start = new HNode(0, new Edge(src, src, 0), heap.getHeap(src).getRoot(), null, null, null);
        pq.add(new EppsteinPath(start, -1, 0));
        for (int i = 0; i < k; i++) {
            EppsteinPath nextEdge = pq.poll();
            if (nextEdge == null) {
                break;
            }
            paths.add(nextEdge);
            explicit.add(nextEdge.listEdges(explicit));
            HNode min = nextEdge.getLastSidetrack();
            HNode crossEdge = min.getCrossEdge();
            HNode left = min.getLeft();
            HNode right = min.getRight();
            HNode outheap = min.getOutheap();
            if (crossEdge != null) {
                pq.add(new EppsteinPath(crossEdge, paths.size() - 1, crossEdge.getKey() + nextEdge.getCost()));
            }
            if (left != null) {
                addToPQ(left, nextEdge, pq, paths);
            }
            if (right != null) {
                addToPQ(right, nextEdge, pq, paths);
            }
            if (outheap != null) {
                addToPQ(outheap, nextEdge, pq, paths);
            }
        }
        return explicit;
    }

    private static void addToPQ(HNode node, EppsteinPath prev, PriorityQueue<EppsteinPath> pq,
            ArrayList<EppsteinPath> paths) {
        if (prev.getPrefPath() == -1) {
            pq.add(new EppsteinPath(node, prev.getPrefPath(), node.getKey()));
        } else {
            pq.add(new EppsteinPath(node, prev.getPrefPath(), node.getKey() + paths.get(prev.getPrefPath()).getCost()));
        }
    }
}

class EppsteinPath implements Comparable<EppsteinPath> {
    private HNode lastSidetrack;
    private int prefPath;
    private int pathCost;

    public EppsteinPath(HNode lastSidetrack, int index, int cost) {
        this.lastSidetrack = lastSidetrack;
        this.prefPath = index;
        this.pathCost = cost;
    }

    public HNode getLastSidetrack() {
        return this.lastSidetrack;
    }

    public void setLastSidetrack(HNode sidetrack) {
        this.lastSidetrack = sidetrack;
    }

    public int getPrefPath() {
        return this.prefPath;
    }

    public void setPrefPath(int newPath) {
        this.prefPath = newPath;
    }

    public int getCost() {
        return this.pathCost;
    }

    public void setCost(int newCost) {
        this.pathCost = newCost;
    }

    public String toString() {
        return "" + this.prefPath;
    }

    public ArrayList<Edge> listEdges(ArrayList<ArrayList<Edge>> paths) {
        ArrayList<Edge> path = new ArrayList<>();
        if (this.prefPath != -1) {
            for (Edge e : paths.get(this.prefPath)) {
                path.add(e);
            }
        }
        path.add(this.lastSidetrack.getEdge());
        return path;
    }

    @Override
    public int compareTo(EppsteinPath o) {
        int cost1 = this.pathCost;
        int cost2 = o.pathCost;
        if (cost1 == cost2) {
            return 0;
        }
        if (cost1 > cost2) {
            return 1;
        }
        return -1;
    }
}