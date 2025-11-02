package main;

import java.util.*;

public class DAGShortestPath {
    private Metrics shortestMetrics;
    private Metrics longestMetrics;

    public DAGShortestPath() {
        this.shortestMetrics = new Metrics("DAG Shortest Path");
        this.longestMetrics = new Metrics("DAG Longest Path");
    }


    public ShortestPathResult computeShortestPaths(Graph graph, List<Integer> topoOrder, int source) {
        int n = graph.getVertices();
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        shortestMetrics.startTimer();

        // Process vertices in topological order
        for (int u : topoOrder) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (Graph.Edge edge : graph.getWeightedNeighbors(u)) {
                    shortestMetrics.incrementOperations();

                    if (dist[u] + edge.weight < dist[edge.to]) {
                        dist[edge.to] = dist[u] + edge.weight;
                        parent[edge.to] = u;
                    }
                }
            }
        }

        shortestMetrics.stopTimer();

        return new ShortestPathResult(dist, parent, shortestMetrics);
    }


    public ShortestPathResult computeLongestPaths(Graph graph, List<Integer> topoOrder, int source) {
        int n = graph.getVertices();
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        longestMetrics.startTimer();

        // Process vertices in topological order
        for (int u : topoOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (Graph.Edge edge : graph.getWeightedNeighbors(u)) {
                    longestMetrics.incrementOperations();

                    if (dist[u] + edge.weight > dist[edge.to]) {
                        dist[edge.to] = dist[u] + edge.weight;
                        parent[edge.to] = u;
                    }
                }
            }
        }

        longestMetrics.stopTimer();

        return new ShortestPathResult(dist, parent, longestMetrics);
    }


    public static List<Integer> reconstructPath(int[] parent, int target) {
        List<Integer> path = new ArrayList<>();
        int curr = target;

        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }

        Collections.reverse(path);
        return path;
    }


    public static class ShortestPathResult {
        public int[] distances;
        public int[] parent;
        public Metrics metrics;

        public ShortestPathResult(int[] distances, int[] parent, Metrics metrics) {
            this.distances = distances;
            this.parent = parent;
            this.metrics = metrics;
        }
    }
}