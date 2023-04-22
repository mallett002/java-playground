package playground.algorithms.graph;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import playground.Node;
import playground.algorithms.graph.CloneGraph;

import java.util.Arrays;

public class CloneGraphTest extends TestCase {
    private Node first;
    private Node second;
    private Node third;
    private Node fourth;

    CloneGraph cloneGraph;
    @BeforeEach
    public void setup() {
        cloneGraph = new CloneGraph();

        first = new Node(1);
        second = new Node(2);
        third = new Node(3);
        fourth = new Node(4);

        first.neighbors.addAll(Arrays.asList(second, fourth));
        second.neighbors.addAll(Arrays.asList(first, third));
        third.neighbors.addAll(Arrays.asList(second, fourth));
        fourth.neighbors.addAll(Arrays.asList(first, third));
    }

    @Test
    public void testPerformClone() {
        Node result = cloneGraph.performClone(first);

    }
}