package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import main.Graph;
import main.TarjanSCC;

import java.util.*;

public class TarjanSCCTest {
    private TarjanSCC tarjan;

    @BeforeEach
    public void setUp() {
        tarjan = new TarjanSCC();
    }

    @Test
    public void testSimpleDAG() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);

        List<List<Integer>> sccs = tarjan.findSCCs(g);

        // Each node should be its own SCC in a DAG
        assertEquals(3, sccs.size());
    }

    @Test
    public void testSingleCycle() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);

        List<List<Integer>> sccs = tarjan.findSCCs(g);

        // All nodes in one SCC
        assertEquals(1, sccs.size());
        assertEquals(3, sccs.get(0).size());
    }

    @Test
    public void testMultipleSCCs() {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 3);

        List<List<Integer>> sccs = tarjan.findSCCs(g);

        assertEquals(2, sccs.size());
    }

    @Test
    public void testSingleNode() {
        Graph g = new Graph(1);

        List<List<Integer>> sccs = tarjan.findSCCs(g);

        assertEquals(1, sccs.size());
        assertEquals(1, sccs.get(0).size());
    }

    @Test
    public void testCondensation() {
        // Create graph with two SCCs
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 0);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 2);
        g.addEdge(3, 4);

        List<List<Integer>> sccs = tarjan.findSCCs(g);
        Graph condensation = tarjan.buildCondensation(g, sccs);

        assertEquals(3, condensation.getVertices());

        assertTrue(condensation.getEdgeCount() < sccs.size() * sccs.size());
    }
}