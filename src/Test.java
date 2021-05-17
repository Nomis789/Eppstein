import java.util.PriorityQueue;
import java.util.Scanner;

public class Test {
    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        // PersistenceHeaps test = new PersistenceHeaps();
        DiGraph dg1 = new DiGraph();
        // NewBinaryHeap<Edge> test2 = new NewBinaryHeap<>();
        // NewBinaryHeap<Edge> test3 = new NewBinaryHeap<>();
        for (int i = 0; i < 12; i++) {
            dg1.addVertex();
        }

        // System.out.println(NewBinaryHeap.parent(4));
        dg1.getVertex(0).addEdge(dg1.getVertex(1), 2);
        dg1.getVertex(0).addEdge(dg1.getVertex(4), 13);
        dg1.getVertex(1).addEdge(dg1.getVertex(2), 20);
        dg1.getVertex(1).addEdge(dg1.getVertex(5), 27);
        dg1.getVertex(2).addEdge(dg1.getVertex(3), 14);
        dg1.getVertex(2).addEdge(dg1.getVertex(6), 14);
        dg1.getVertex(3).addEdge(dg1.getVertex(7), 15);
        dg1.getVertex(4).addEdge(dg1.getVertex(5), 9);
        dg1.getVertex(4).addEdge(dg1.getVertex(8), 15);
        dg1.getVertex(5).addEdge(dg1.getVertex(6), 10);
        dg1.getVertex(5).addEdge(dg1.getVertex(9), 20);
        dg1.getVertex(6).addEdge(dg1.getVertex(7), 25);
        dg1.getVertex(6).addEdge(dg1.getVertex(10), 12);
        dg1.getVertex(7).addEdge(dg1.getVertex(11), 7);
        dg1.getVertex(8).addEdge(dg1.getVertex(9), 18);
        dg1.getVertex(9).addEdge(dg1.getVertex(10), 8);
        dg1.getVertex(10).addEdge(dg1.getVertex(11), 11);

        // dg1.getVertex(0).addEdge(dg1.getVertex(1), 0);
        // dg1.getVertex(0).addEdge(dg1.getVertex(4), 1);
        // dg1.getVertex(0).addEdge(dg1.getVertex(4), 6);
        // dg1.getVertex(0).addEdge(dg1.getVertex(4), 12);
        // dg1.getVertex(0).addEdge(dg1.getVertex(4), 14);
        // dg1.getVertex(1).addEdge(dg1.getVertex(2), 0);
        // dg1.getVertex(1).addEdge(dg1.getVertex(4), 13);
        // dg1.getVertex(2).addEdge(dg1.getVertex(4), 0);
        // dg1.getVertex(2).addEdge(dg1.getVertex(4), 17);
        // dg1.getVertex(2).addEdge(dg1.getVertex(4), 19);
        // dg1.getVertex(3).addEdge(dg1.getVertex(2), 0);
        // dg1.getVertex(3).addEdge(dg1.getVertex(4), 3);
        // dg1.getVertex(3).addEdge(dg1.getVertex(4), 7);
        // dg1.getVertex(4).addEdge(dg1.getVertex(4), 4);
        // dg1.getVertex(4).addEdge(dg1.getVertex(4), 8);
        // dg1.getVertex(4).addEdge(dg1.getVertex(4), 10);

        // for (int i = 0; i < 10; i++) {
        // test2.insert(i, new Edge(new Vertex(i), new Vertex(i), i));
        // }

        // ArrayList<NewBinaryHeap<Edge>> lol = new ArrayList<>();
        // lol.add(test2);

        // test3 = test2.insertWithCopies(-1, new Edge(new Vertex(-1), new Vertex(-1),
        // -1));

        // lol.add(test3);

        // System.out.println(lol.get(0).getSize());

        // System.out.println(lol.get(1).getSize());

        // dg1.reverse();

        Eppstein.kShortestPaths(dg1.getVertex(0), dg1.getVertex(11), dg1, 11);

        // System.out.println(dg1);

        // System.out.println(Dijkstra.shortestPathTree(dg1.getVertex(11), dg1));

        // System.out.println(dg1);

        // DiGraph test = new DiGraph();
        // test.addVertex();
        // test.addVertex();

        // Edge e = new Edge(test.getVertex(0), test.getVertex(1), 10);
        // test.addEdge(test.getVertex(0), e);

        // System.out.println(test.edges);
        // test.reverse();
        // System.out.println(test.edges);
    }

    private static void buildGraph() {
        System.out.println("Hi, welcome to the graph creator");
        DiGraph g = new DiGraph();
        while (s.nextLine() != "exit") {
            displayOptions();
            int command = s.nextInt();
            if (command == 1) {
                System.out.println("How many lines do you wish to add?");
                for (int i = 0; i < s.nextInt(); i++) {
                    g.addVertex();
                }
            } else if (command == 2) {
                System.out.println("What is the source vertex?");
                Vertex v = g.getVertex(s.nextInt());
                System.out.println();
            }
        }
    }

    private static void displayOptions() {
        System.out.println("Here are your options:");
        System.out.println("1. Add vertices");
        System.out.println("2. Add edges to vertex");
        System.out.println("3. Display graph");
    }
}
