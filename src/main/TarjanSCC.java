package main;
import java.util.*;


public class TarjanSCC {
    private int index;
    private int[] indices;
    private int[] lowlink;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> sccs;
    private Metrics metrics;

    public TarjanSCC() {
        this.metrics = new Metrics("Tarjan SCC");
    }


    public List<List<Integer>> findSCCs(Graph graph) {
        int n = graph.getVertices();
        indices = new int[n];
        lowlink = new int[n];
        onStack = new boolean[n];
        Arrays.fill(indices, -1);

        stack = new Stack<>();
        sccs = new ArrayList<>();
        index = 0;

        metrics.startTimer();

        for (int v = 0; v < n; v++) {
            if (indices[v] == -1) {
                strongConnect(v, graph);
            }
        }

        metrics.stopTimer();

        return sccs;
    }

    private void strongConnect(int v, Graph graph) {
        indices[v] = index;
        lowlink[v] = index;
        index++;
        stack.push(v);
        onStack[v] = true;

        metrics.incrementOperations(); // DFS visit count

        for (int w : graph.getNeighbors(v)) {
            metrics.incrementOperations(); // Edge exploration count

            if (indices[w] == -1) {
                strongConnect(w, graph);
                lowlink[v] = Math.min(lowlink[v], lowlink[w]);
            } else if (onStack[w]) {
                lowlink[v] = Math.min(lowlink[v], indices[w]);
            }
        }

        // If v is a root node, pop the stack to get SCC
        if (lowlink[v] == indices[v]) {
            List<Integer> scc = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack[w] = false;
                scc.add(w);
            } while (w != v);
            sccs.add(scc);
        }
    }

    public Metrics getMetrics() {
        return metrics;
    }


    public Graph buildCondensation(Graph original, List<List<Integer>> sccs) {
        int n = original.getVertices();
        int[] nodeToScc = new int[n];

        // Map each node to its SCC index
        for (int i = 0; i < sccs.size(); i++) {
            for (int node : sccs.get(i)) {
                nodeToScc[node] = i;
            }
        }

        Graph condensation = new Graph(sccs.size());
        Set<String> addedEdges = new HashSet<>();

        // Add edges between different SCCs
        for (int v = 0; v < n; v++) {
            for (int neighbor : original.getNeighbors(v)) {
                int sccV = nodeToScc[v];
                int sccNeighbor = nodeToScc[neighbor];

                if (sccV != sccNeighbor) {
                    String edge = sccV + "-" + sccNeighbor;
                    if (!addedEdges.contains(edge)) {
                        condensation.addEdge(sccV, sccNeighbor);
                        addedEdges.add(edge);
                    }
                }
            }
        }

        return condensation;
    }
}
