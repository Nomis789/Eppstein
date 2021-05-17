import java.util.HashMap;
import java.util.Iterator;

public class PersistenceHeaps implements Iterable<BH> {
    private HashMap<Vertex, BH> roots;

    public PersistenceHeaps() {
        this.roots = new HashMap<>();
    }

    public void persistentInsert(Vertex v, BH prev, OutHeap heap) {
        BH entry = prev.insertWithCopies(heap.getOutroot());
        this.roots.put(v, entry);
    }

    public void put(Vertex v, BH heap) {
        this.roots.put(v, heap);
    }

    public BH getHeap(Vertex v) {
        return this.roots.get(v);
    }

    public String toString() {
        return roots.toString();
    }

    public Iterator<BH> iterator() {
        return this.roots.values().iterator();
    }
}
