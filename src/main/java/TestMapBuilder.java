import java.util.List;
import java.util.Map;

public class TestMapBuilder {
  public static void main(String[] args) {
    // create instance
    //String[] names = {"CsvCombinerTest1.csv", "CsvCombinerTest2.csv"};

    String[] names = {
      "Stockton TSP Project 5- MAIN (00s).csv",
      "Stockton TSP Project 5- LAYER 2 (100s).csv",
      "Stockton TSP Project 5- LAYER 3 (200s).csv",
      "Stockton TSP Project 5- Stairways.csv",
      "Stockton TSP Project 5- Academic Spine Walk.csv",
      "Stockton TSP Project 5- Lines.csv"
    };
    CSVCombiner.combineCSVs(names, "csvCombinerTestDestination.csv");
    CSVCombiner.sortCSV("csvCombinerTestDestination.csv");

    MapBuilder builder = new MapBuilder();
    builder.buildMap("src/main/resources/csvCombinerTestDestination.csv");

    // get the map
    Map<String, Node> map = builder.getMap();

    /*
    System.out.println("=== FULL NODE MAP ===");
    for (String nodeName : map.keySet()) {
        Node node = map.get(nodeName);
        System.out.println(node);
    }
    */

    GraphBuilder testGraphBuilder = new GraphBuilder(map);
    double[][] dist = testGraphBuilder.getDistMatrix();
    int[][] next = testGraphBuilder.getNextMatrix();
    FloydWarshall.compute(dist, next);

    int[] solution = TSPRunner.solveTSP(dist);

    for (int i = 0; i < solution.length; i++) {
      System.out.print(testGraphBuilder.getIndex().get((solution[i])));
    }
    System.out.println("\n");

    System.out.println(builder.getMap().values());

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        List<Integer> pathList = FloydWarshall.getPath(i, j, next);
        if (pathList != null) {
          for (int index : pathList) {
            System.out.print(testGraphBuilder.getIndex().get(index));
          }
        }
        System.out.println(": " + java.lang.Math.round(dist[i][j]));
      }
    }

    // System.out.println(testGraphBuilder.getIndex());

    /*
    for (double[] row : dist) {
        for(double value : row) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
        */

  }
}
