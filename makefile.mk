JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Edge.java \
        Vertex.java \
        DiGraph.java \
		Node.java \
		Leaf.java \
		QuakeHeap.java \
        HeapNode.java \
        NewBinaryHeap.java \
        PersistenceHeaps.java \
		Eppstein.java 


default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class