import java.util.*;

public class FloydWarshall {

  /**
   * Runs Floyd–Warshall on the given dist + next matrices.
   *
   * <p>dist[i][j] will contain the SHORTEST distance between i and j afterward. next[i][j] will
   * contain the NEXT NODE on the shortest path from i to j.
   *
   * <p>This means after running this, you have: - all-pairs shortest distances - path
   * reconstruction ability
   */
  public static void compute(double[][] dist, int[][] next) {
    int n = dist.length;

    // Triple nested loop: k, then i, then j
    // Meaning: we test whether going i → k → j is better than i → j
    for (int k = 0; k < n; k++) {

      for (int i = 0; i < n; i++) {

        // Skip if no path from i → k
        if (dist[i][k] == Double.POSITIVE_INFINITY) continue;

        for (int j = 0; j < n; j++) {

          // Skip if no path from k → j
          if (dist[k][j] == Double.POSITIVE_INFINITY) continue;

          // Check if going through k improves the path i → j
          double newDist = dist[i][k] + dist[k][j];

          if (newDist < dist[i][j]) {
            dist[i][j] = newDist; // Update shortest distance
            next[i][j] = next[i][k]; // Next hop is same as path to k
          }
        }
      }
    }
  }

  /**
   * Reconstructs the actual path from node u to node v. Uses the next[][] matrix built by
   * Floyd–Warshall.
   *
   * <p>Example: getPath(Entrance, D_WING_MAIN) Might return: [Entrance, A_WING_ENTRANCE,
   * A_WING_MAIN, B_WING_MAIN, D_WING_MAIN]
   */
  public static List<Integer> getPath(int u, int v, int[][] next) {

    // If there is no path, return null
    if (next[u][v] == -1) {
      return null;
    }

    List<Integer> path = new ArrayList<>();
    path.add(u);

    // Follow the next-hop pointers until we reach the destination
    while (u != v) {
      u = next[u][v]; // move one step forward on the path
      path.add(u);
    }

    return path;
  }
}
