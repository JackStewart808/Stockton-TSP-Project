import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MapBuilder {

  private final Map<String, Node> map; // holds all nodes

  public MapBuilder() {
    map = new HashMap<>();
  }

  // read the map data from the csv and build the map
  public void buildMap(String csvFile) {
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      boolean pointsDone = false;

      while ((line = br.readLine()) != null) {
        if (line.isEmpty() || line.charAt(0) != '"') continue;

        // =====================
        // POINT rows
        // =====================
        if (line.charAt(1) == 'P') {
          if (pointsDone) {
            System.out.println("Warning: POINT found after LINE");
          }

          String[] parts = line.split(",", 4);
          if (parts.length < 3) continue;

          String pointPart = parts[0].replace("\"", "").trim();

          int start = pointPart.indexOf("(");
          int end = pointPart.indexOf(")");
          if (start < 0 || end < 0) continue;

          String[] coordParts = pointPart.substring(start + 1, end).trim().split(" ");
          if (coordParts.length != 2) continue;

          double latitude = Double.parseDouble(coordParts[0]);
          double longitude = Double.parseDouble(coordParts[1]);

          String name = parts[1].trim();
          String floorString = parts[2].trim();

          boolean isStaircase = floorString.equals("0-1") || floorString.equals("1-2");

          map.put(name, new Node(name, latitude, longitude, isStaircase, new ArrayList<>()));
        }

        // =====================
        // LINE rows
        // =====================
        else if (line.charAt(1) == 'L') {
          pointsDone = true;

          // Split CSV into WKT, name, description
          String[] csvParts = line.split(",", 3);
          String description = csvParts.length >= 3 ? csvParts[2].replace("\"", "").trim() : "";

          // Proper LINESTRING parsing
          int start = line.indexOf("(");
          int end = line.indexOf(")");
          if (start < 0 || end < 0) continue;

          String coordsOnly = line.substring(start + 1, end);
          String[] pairs = coordsOnly.split(",");

          if (pairs.length != 2) {
            System.out.println("Skipping malformed LINE: " + line);
            continue;
          }

          double[] point1;
          double[] point2;

          try {
            String[] p1 = pairs[0].trim().split(" ");
            String[] p2 = pairs[1].trim().split(" ");

            point1 = new double[] {Double.parseDouble(p1[0]), Double.parseDouble(p1[1])};

            point2 = new double[] {Double.parseDouble(p2[0]), Double.parseDouble(p2[1])};
          } catch (Exception e) {
            System.out.println("Skipping bad LINE coords: " + line);
            continue;
          }

          // Description-defined endpoints override geometry
          if (description.contains("->")) {
            connectUsingDescription(description);
          } else {
            Node node1 = getClosestNode(point1);
            Node node2 = getClosestNode(point2);

            node1.connections.add(node2.name);
            node2.connections.add(node1.name);
          }
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading CSV file: " + e.getMessage());
    }
  }

  private Node getClosestNode(double[] point) {
    double smallestDistance = Double.MAX_VALUE;
    double distance;
    Node desiredNode = new Node(point);
    Node bestNode = desiredNode;
    for (Node node : map.values()) {
      distance = DistanceCalculator.feet(desiredNode, node);
      if (distance < smallestDistance) {
        smallestDistance = distance;
        bestNode = node;
      }
    }
    if (bestNode.equals(desiredNode)) {
      System.out.println("No valid Node was found.");
    }
    return bestNode;
  }

  private void connectUsingDescription(String description) {
    if (description == null || !description.contains("->")) return;

    String[] parts = description.split("->");
    if (parts.length != 2) return;

    String from = parts[0].trim();
    String to = parts[1].trim();

    Node a = map.get(from);
    Node b = map.get(to);

    if (a != null && b != null) {
      a.connections.add(b.name);
      b.connections.add(a.name);
    }
  }

  private double parseCleanDouble(String s) {
    return Double.parseDouble(s.replaceAll("[^0-9.-]", ""));
  }

  /*
  public void oldBuildMap(String csvFile) {
      Pattern pointPattern = Pattern.compile("POINT \\((-?[0-9.]+) (-?[0-9.]+)\\)");

      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
          String line;
          boolean skipHeader = true;

          while ((line = br.readLine()) != null) {
              if (skipHeader) {
                  skipHeader = false;
                  continue; // skip header
              }

              List<String> parts = splitCsvLine(line);
              if (parts.size() < 4) continue;

              String wkt = parts.get(0).trim();
              String name = parts.get(1).trim();
              String floorString = parts.get(2).trim();
              String connectionText = parts.get(3).trim();

              // extract coordinates
              Matcher m = pointPattern.matcher(wkt);
              double lon = 0, lat = 0;
              if (m.find()) {
                  lon = Double.parseDouble(m.group(1));
                  lat = Double.parseDouble(m.group(2));
              }

              //Set the floor, if inbetween set to 0.5 or 1.5
              double floor;
              floor = switch (floorString) {
                  case "0-1" -> 0.5;
                  case "1-2" -> 1.5;
                  default -> Double.parseDouble(floorString);
              };


              // parse connections
              String[] connectionArray = connectionText.split(":");
              List<String> connections = new ArrayList<>();
              for (String c : connectionArray) {
                  c = c.trim();
                  if (!c.isEmpty()) {
                      connections.add(c);
                  }
              }

              Node node = new Node(name, lat, lon, isStiarcase, connections);
              map.put(name, node);
          }

      } catch (IOException e) {
          System.out.println("Error reading CSV file: " + e.getMessage());
      }
  }
  */

  public Map<String, Node> getMap() {
    return map;
  }
}
