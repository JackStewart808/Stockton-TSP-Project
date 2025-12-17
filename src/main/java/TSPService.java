import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TSPService {

  public static String[] solve(String[] points) {
    MapBuilder builder = new MapBuilder();
    builder.buildMap("src/main/resources/AllData.csv");

    Map<String, Node> map = builder.getMap();

    System.out.println("=== Map keys ===");
    System.out.println(map.size());
    for (String key : map.keySet()) {
      System.out.println(key);
    }
    System.out.println("================");

    GraphBuilder graphBuilder = new GraphBuilder(map);
    double[][] dist = graphBuilder.getDistMatrix();
    int[][] next = graphBuilder.getNextMatrix();
    FloydWarshall.compute(dist, next);

    List<Integer> pointIndexes = new ArrayList<>();
    for (String p : points) {
      int idx = graphBuilder.getIndex().indexOf(p);
      if (idx >= 0) {
        pointIndexes.add(idx);
      } else {
        throw new IllegalArgumentException("Point not found in map: " + p);
      }
    }

    double[][] newDist = GraphBuilder.buildSubMatrix(dist, pointIndexes);

    int[] solution = TSPRunner.solveTSP(newDist);

    String[] solutionString = new String[solution.length];
    for (int i = 0; i < solution.length; i++) {
      solutionString[i] = graphBuilder.getIndex().get(pointIndexes.get(solution[i]));
    }

    return solutionString;
  }
}
