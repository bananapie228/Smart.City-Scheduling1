package main;

import java.util.*;

public class Graph {
    private int vertices;
    private List<List<Integer>> adjList;
    private List<List<Edge>> weightedAdjList;

    public static class Edge {
        public int to;
        public int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>();
        this.weightedAdjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
            weightedAdjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        adjList.get(from).add(to);
    }

    public void addWeightedEdge(int from, int to, int weight) {
        adjList.get(from).add(to);
        weightedAdjList.get(from).add(new Edge(to, weight));
    }

    public int getVertices() {
        return vertices;
    }

    public List<Integer> getNeighbors(int v) {
        return adjList.get(v);
    }

    public List<Edge> getWeightedNeighbors(int v) {
        return weightedAdjList.get(v);
    }

    public Graph getTranspose() {
        Graph transpose = new Graph(vertices);
        for (int v = 0; v < vertices; v++) {
            for (int neighbor : adjList.get(v)) {
                transpose.addEdge(neighbor, v);
            }
        }
        return transpose;
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<Integer> neighbors : adjList) {
            count += neighbors.size();
        }
        return count;
    }
}
