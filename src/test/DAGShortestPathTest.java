package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import main.Graph;
import main.TopologicalSort;
import main.DAGShortestPath;
import main.DAGShortestPath.ShortestPathResult;
import java.util.*;

public class DAGShortestPathTest {
    private DAGShortestPath dagSP;

    @BeforeEach
    public void setUp() {
        dagSP = new DAGShortestPath();
    }

    @Test
    public void testShortestPathLinear() {

        Graph g = new Graph(4);
        g.addWeightedEdge(0, 1, 2);
        g.addWeightedEdge(1, 2, 3);
        g.addWeightedEdge(2, 3, 1);

        TopologicalSort topo = new TopologicalSort();
        List<Integer> order = topo.sort(g);

        ShortestPathResult result = dagSP.computeShortestPaths(g, order, 0);

        assertEquals(0, result.distances[0]);
        assertEquals(2, result.distances[1]);
        assertEquals(5, result.distances[2]);
        assertEquals(6, result.distances[3]);
    }

    @Test
    public void testShortestPathMultiplePaths() {

        Graph g = new Graph(4);
        g.addWeightedEdge(0, 1, 5);
        g.addWeightedEdge(0, 2, 2);
        g.addWeightedEdge(1, 3, 2);
        g.addWeightedEdge(2, 3, 1);

        TopologicalSort topo = new TopologicalSort();
        List<Integer> order = topo.sort(g);

        ShortestPathResult result = dagSP.computeShortestPaths(g, order, 0);

        assertEquals(3, result.distances[3]); // Should take path 0->2->3
    }

    @Test
    public void testLongestPath() {

        Graph g = new Graph(3);
        g.addWeightedEdge(0, 1, 2);
        g.addWeightedEdge(1, 2, 3);
        g.addWeightedEdge(0, 2, 1);

        TopologicalSort topo = new TopologicalSort();
        List<Integer> order = topo.sort(g);

        ShortestPathResult result = dagSP.computeLongestPaths(g, order, 0);

        assertEquals(5, result.distances[2]); // Should take path 0->1->2
    }

    @Test
    public void testPathReconstruction() {
        Graph g = new Graph(4);
        g.addWeightedEdge(0, 1, 1);
        g.addWeightedEdge(1, 2, 1);
        g.addWeightedEdge(2, 3, 1);

        TopologicalSort topo = new TopologicalSort();
        List<Integer> order = topo.sort(g);

        ShortestPathResult result = dagSP.computeShortestPaths(g, order, 0);
        List<Integer> path = DAGShortestPath.reconstructPath(result.parent, 3);

        assertEquals(Arrays.asList(0, 1, 2, 3), path);
    }
}