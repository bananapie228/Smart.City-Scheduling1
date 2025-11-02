
package test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import main.Graph;
import main.TopologicalSort;
import java.util.*;

public class TopologicalSortTest {
    private TopologicalSort topoSort;

    @BeforeEach
    public void setUp() {
        topoSort = new TopologicalSort();
    }

    @Test
    public void testLinearDAG() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        List<Integer> order = topoSort.sort(g);

        assertNotNull(order);
        assertEquals(4, order.size());
        assertEquals(Arrays.asList(0, 1, 2, 3), order);
    }

    @Test
    public void testDAGWithMultiplePaths() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        List<Integer> order = topoSort.sort(g);

        assertNotNull(order);
        assertEquals(4, order.size());
        assertEquals(0, order.get(0)); // 0 must be first
        assertEquals(3, order.get(3)); // 3 must be last
    }

    @Test
    public void testGraphWithCycle() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);

        List<Integer> order = topoSort.sort(g);

        // Should return null for cyclic graph
        assertNull(order);
    }

    @Test
    public void testSingleNode() {
        Graph g = new Graph(1);

        List<Integer> order = topoSort.sort(g);

        assertNotNull(order);
        assertEquals(1, order.size());
        assertEquals(0, order.get(0));
    }

    @Test
    public void testDisconnectedDAG() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(2, 3);

        List<Integer> order = topoSort.sort(g);

        assertNotNull(order);
        assertEquals(4, order.size());
    }
}