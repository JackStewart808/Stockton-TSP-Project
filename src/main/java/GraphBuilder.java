import java.util.*;

public class GraphBuilder {

  private final Map<String, Node> map; // All nodes loaded from CSV
  private final List<String> index; // Converts node names -> numbered indexes
  private final double[][] dist; // Distance matrix (adjacency matrix)
  private final int[][] next; // For path reconstruction

  public static final double INF = Double.POSITIVE_INFINITY;

  public GraphBuilder(Map<String, Node> map) {
    this.map = map;

    // Convert map keys to a list, so each node gets an index (0,1,2,...)
    // Example: index.get(0) might be "Entrance_AaS"
    this.index = new ArrayList<>(map.keySet());

    int n = index.size();

    // Create n x n matrices for distances and next hops
    dist = new double[n][n];
    next = new int[n][n];

    // Fill matrices with initial values
    initMatrices();

    // Fill in edges (direct connections only) from your CSV data
    fillEdges();
  }

  /**
   * Initialize distance + next matrices. Before Floyd–Warshall runs, we set: - dist[i][i] = 0
   * (distance to itself) - dist[i][j] = INF for all others - next[i][i] = i - next[i][j] = -1
   * (unknown path)
   */
  private void initMatrices() {
    int n = index.size();

    for (int i = 0; i < n; i++) {
      Arrays.fill(dist[i], INF);
      Arrays.fill(next[i], -1);

      dist[i][i] = 0; // distance to itself is 0
      next[i][i] = i; // next hop to itself = itself
    }
  }

  /**
   * Fill edges based on the node's connections. This builds the DIRECT edges graph. Example: if A
   * has a connection to B, we compute distance(A,B), and set dist[A][B] to that distance.
   */
  private void fillEdges() {
    int n = index.size();

    for (int i = 0; i < n; i++) {
      Node a = map.get(index.get(i));

      // For each neighbor listed in its "connections"
      for (String neighbor : a.connections) {
        Node b = map.get(neighbor);
        if (b == null) continue;

        // Convert neighbor name → index in matrix
        int j = index.indexOf(neighbor);

        // Calculate walking distance ONLY if they are directly connected
        double d = DistanceCalculator.feet(a, b);

        // If valid connection
        if (d >= 0) {
          dist[i][j] = d; // direct edge weight
          next[i][j] = j; // next hop from i to j is j
        }
      }
    }
  }

  public double[][] getDistMatrix() {
    return dist;
  }

  public int[][] getNextMatrix() {
    return next;
  }

  public List<String> getIndex() {
    return index;
  }
}
