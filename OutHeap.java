import java.util.Arrays;
import java.util.List;

public class OutHeap {
    private HNode outroot;

    public OutHeap(Vertex v) {
        Edge[] edges = v.getEdges();
        if (edges.length == 0) {
            this.outroot = null;
            return;
        }

        BH rest = new BH();

        List<Edge> list = Arrays.asList(edges);
        int min = list.stream().mapToInt((e) -> e.getWeight()).min().getAsInt();
        this.outroot = new HNode(min, list.stream().filter((e) -> e.getWeight() == min).findFirst().get());

        for (Edge edge : edges) {
            if (!edge.equals(this.outroot.getEdge())) {
                rest.insert(edge.getWeight(), edge);
            }
        }

        this.outroot.setOutheap(rest.getRoot());
    }

    public HNode getOutroot() {
        return this.outroot;
    }
}
