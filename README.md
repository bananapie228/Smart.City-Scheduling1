# Smart City / Smart Campus Scheduling - Algorithm Implementation

## Introduction

This project implements several graph-based algorithms for scheduling city-service tasks, such as street cleaning, repairs, and sensor maintenance. The goal is to handle both cyclic and acyclic dependencies efficiently, utilizing algorithms like Tarjan's for Strongly Connected Components (SCC), Topological Sorting, and Shortest Paths in Directed Acyclic Graphs (DAGs).

These algorithms are applied to a dataset representing tasks and their dependencies, and performance metrics are tracked for further analysis.

## Algorithm Overview

### 1. Strongly Connected Components (SCC)
Tarjan's algorithm is implemented to find strongly connected components in a directed graph. SCCs are groups of vertices in which every vertex is reachable from every other vertex within the group. 

The algorithm uses a depth-first search (DFS) approach, where each vertex is assigned an index and low-link value. If a vertex's low-link value equals its index, an SCC is found and the vertices in that SCC are recorded.

### 2. Topological Sorting
Topological sorting is performed on the condensation graph of SCCs. The condensation graph is a Directed Acyclic Graph (DAG) formed by merging all SCCs. The topological sort orders the nodes such that for every directed edge u → v, u comes before v in the ordering.

### 3. Shortest Path in a DAG
A shortest path algorithm is implemented for DAGs, which finds the shortest distance from a source node to all other nodes in the graph. Additionally, the longest path is calculated by inverting edge weights or using dynamic programming over the topologically sorted graph.

## Dataset Generation

The datasets are generated with various graph sizes and structures. Each dataset is stored under the `/data` folder and includes the following categories:

- **Small (6–10 nodes)**: Simple cases with few or no cycles.
- **Medium (10–20 nodes)**: Mixed structures with several SCCs.
- **Large (20–50 nodes)**: Used for performance and timing tests.

Each dataset varies in density and includes both cyclic and acyclic examples.

#### Example Datasets:
1. **Small Dataset 1**:
   - Nodes: 6
   - Edges: 7
   - Type: Acyclic
   - Features: Linear DAG, no cycles.
   
2. **Small Dataset 2**:
   - Nodes: 7
   - Edges: 10
   - Type: Cyclic
   - Features: Simple cycle with one disconnected DAG component.
   
3. **Medium Dataset 1**:
   - Nodes: 15
   - Edges: 20
   - Type: Acyclic
   - Features: A mixture of sparse edges and isolated nodes.
   
4. **Medium Dataset 2**:
   - Nodes: 18
   - Edges: 28
   - Type: Cyclic
   - Features: Multiple SCCs with interdependencies.
   
5. **Large Dataset 1**:
   - Nodes: 30
   - Edges: 50
   - Type: Acyclic
   - Features: Dense DAG, large-scale scheduling tasks.
   
6. **Large Dataset 2**:
   - Nodes: 40
   - Edges: 80
   - Type: Cyclic
   - Features: Multiple cycles and long chains of dependencies.

## Setup and Installation

To set up the project, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/bananapie228/Smart.City-Scheduling1.git
   cd Smart.City-Scheduling1
Install dependencies (if using Maven, or any other package manager):

bash
Copy code
mvn install
Build the project:

bash
Copy code
mvn clean install
Run the project:

bash
Copy code
mvn exec:java
Usage
To run the algorithms on your own dataset, simply replace the existing datasets in the /data folder with your own, and rerun the main program.

Running the Algorithms
To compute SCCs, topological sort, and shortest paths on a graph, use the following command:

bash
Copy code
java -cp target/smart-city-scheduling-1.0-SNAPSHOT.jar main.Main
Dataset Generation
Use the DatasetGenerator class to generate test datasets. You can customize the number of nodes and edges, as well as the density of the graph.

Testing
Unit tests are included for the algorithms, particularly for edge cases and small deterministic inputs. To run the tests, use:

bash
Copy code
mvn test
Performance Metrics
The project tracks the following performance metrics:

DFS visits/edges during SCC computation.

Pops/pushes during topological sorting.

Relaxations for shortest path calculation.

These metrics are logged and can be found in the output of the program.

# Results and Analysis
The project generates tables that summarize the performance (operation counts, time) for each graph category. We observe that the algorithms scale well with increasing graph size, although bottlenecks arise in highly dense graphs due to the complexity of SCC detection and topological sorting.

# Conclusions
Tarjan's Algorithm for SCC is efficient for finding all SCCs, even in large graphs.

Topological Sorting is crucial for organizing tasks in an optimal order for DAGs.

Shortest Path Algorithms in DAGs are essential for determining the minimal task durations.

This project demonstrates the effectiveness of graph algorithms in scheduling and task management, and offers insights into which methods to apply based on graph structure.

