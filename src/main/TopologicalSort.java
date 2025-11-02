package main;
import java.util.*;

public class TopologicalSort {
    private Metrics metrics;

    public TopologicalSort() {
        this.metrics = new Metrics("Topological Sort (Kahn)");
    }


    public List<Integer> sort(Graph graph) {
        int n = graph.getVertices();
        int[] inDegree = new int[n];

        // Calculate in-degrees
        for (int v = 0; v < n; v++) {
            for (int neighbor : graph.getNeighbors(v)) {
                inDegree[neighbor]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();

        metrics.startTimer();

        // Add all vertices with in-degree 0
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                metrics.incrementOperations(); // Queue push count
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            metrics.incrementOperations(); // Queue pop count
            result.add(u);

            for (int v : graph.getNeighbors(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.offer(v);
                    metrics.incrementOperations(); // Queue push count
                }
            }
        }

        metrics.stopTimer();

        // Check if all vertices were processed (no cycle)
        if (result.size() != n) {
            return null; // Graph has a cycle
        }

        return result;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}