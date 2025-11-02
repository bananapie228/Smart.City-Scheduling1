package main;

import java.io.*;
import java.util.*;


public class DatasetGenerator {
    private Random random;

    public DatasetGenerator(long seed) {
        this.random = new Random(seed);
    }


    public void generateDataset(String filename, int nodes, int edges,
                                boolean includeCycles, String description) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("{");
            writer.println("  \"description\": \"" + description + "\",");
            writer.println("  \"nodes\": " + nodes + ",");
            writer.println("  \"edges\": [");

            Set<String> edgeSet = new HashSet<>();
            List<String> edgeList = new ArrayList<>();

            if (includeCycles) {
                // Generate graph with cycles
                generateWithCycles(nodes, edges, edgeSet, edgeList);
            } else {
                // Generate pure DAG
                generateDAG(nodes, edges, edgeSet, edgeList);
            }

            for (int i = 0; i < edgeList.size(); i++) {
                writer.print("    " + edgeList.get(i));
                if (i < edgeList.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }

            writer.println("  ]");
            writer.println("}");

            System.out.println("Generated: " + filename);
            System.out.println("  Nodes: " + nodes + ", Edges: " + edgeList.size());
            System.out.println("  Type: " + (includeCycles ? "Cyclic" : "DAG"));
            System.out.println();

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private void generateDAG(int nodes, int targetEdges, Set<String> edgeSet, List<String> edgeList) {
        // Create a DAG by only adding edges from lower to higher numbered nodes
        int attempts = 0;
        while (edgeList.size() < targetEdges && attempts < targetEdges * 10) {
            int from = random.nextInt(nodes - 1);
            int to = from + 1 + random.nextInt(nodes - from - 1);
            int weight = 1 + random.nextInt(10);

            String edge = from + "," + to;
            if (!edgeSet.contains(edge)) {
                edgeSet.add(edge);
                edgeList.add("{\"from\": " + from + ", \"to\": " + to + ", \"weight\": " + weight + "}");
            }
            attempts++;
        }
    }

    private void generateWithCycles(int nodes, int targetEdges, Set<String> edgeSet, List<String> edgeList) {
        // First create some cycles
        int numCycles = 1 + random.nextInt(3);
        for (int c = 0; c < numCycles; c++) {
            int cycleSize = 2 + random.nextInt(Math.min(4, nodes / 2));
            int start = random.nextInt(nodes - cycleSize);

            for (int i = 0; i < cycleSize; i++) {
                int from = start + i;
                int to = start + ((i + 1) % cycleSize);
                int weight = 1 + random.nextInt(10);

                String edge = from + "," + to;
                if (!edgeSet.contains(edge)) {
                    edgeSet.add(edge);
                    edgeList.add("{\"from\": " + from + ", \"to\": " + to + ", \"weight\": " + weight + "}");
                }
            }
        }

        // Add remaining random edges
        int attempts = 0;
        while (edgeList.size() < targetEdges && attempts < targetEdges * 10) {
            int from = random.nextInt(nodes);
            int to = random.nextInt(nodes);
            int weight = 1 + random.nextInt(10);

            if (from != to) {
                String edge = from + "," + to;
                if (!edgeSet.contains(edge)) {
                    edgeSet.add(edge);
                    edgeList.add("{\"from\": " + from + ", \"to\": " + to + ", \"weight\": " + weight + "}");
                }
            }
            attempts++;
        }
    }

    public static void main(String[] args) {
        DatasetGenerator generator = new DatasetGenerator(42);

        System.out.println("=== Generating Test Datasets ===\n");

        // Small datasets (6-10 nodes)
        generator.generateDataset("data/small1_dag.json", 6, 8, false,
                "Small pure DAG - simple dependency chain");
        generator.generateDataset("data/small2_cycle.json", 7, 10, true,
                "Small graph with single cycle");
        generator.generateDataset("data/small3_multi.json", 8, 12, true,
                "Small graph with multiple cycles");

        // Medium datasets (10-20 nodes)
        generator.generateDataset("data/medium1_mixed.json", 12, 18, true,
                "Medium mixed structure with several SCCs");
        generator.generateDataset("data/medium2_dense.json", 15, 35, true,
                "Medium dense graph with interconnected SCCs");
        generator.generateDataset("data/medium3_sparse.json", 18, 22, false,
                "Medium sparse DAG - long chains");

        // Large datasets (20-50 nodes)
        generator.generateDataset("data/large1_complex.json", 30, 55, true,
                "Large complex graph with multiple SCCs");
        generator.generateDataset("data/large2_dense.json", 40, 120, true,
                "Large dense network for performance testing");
        generator.generateDataset("data/large3_performance.json", 50, 80, false,
                "Large sparse DAG for performance testing");

        System.out.println("All datasets generated successfully!");
    }
}
