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
      // Loop that iterates over lines in txt file
      while ((line = br.readLine()) != null) {
        if (line.charAt(0) != '\"') {
          continue;
        }

        // Import Points
        if (line.charAt(1) == 'P') {
          if (pointsDone) {
            System.out.println("A point was handled after a line");
          }

          String[] parts = line.split(",", 4);
          if (parts.length < 3) {
            continue;
          }

          // Extract coordinates
          String pointPart = parts[0].trim();
          // Remove leading/trailing quotes if present
          pointPart = pointPart.replace("\"", "");
          // Now pointPart looks like: POINT (-74.5298027 39.4933532)

          int start = pointPart.indexOf("(");
          int end = pointPart.indexOf(")");
          if (start < 0 || end < 0) {
            continue;
          }

          String coords = pointPart.substring(start + 1, end).trim();
          String[] coordParts = coords.split(" ");
          if (coordParts.length != 2) {
            continue;
          }

          double latitude = Double.parseDouble(coordParts[0]);
          double longitude = Double.parseDouble(coordParts[1]);

          // Node name
          String name = parts[1].trim();

          // Floor (single digit)
          String floorString = parts[2].trim();
          boolean isStaircase;
          isStaircase =
              switch (floorString) {
                case "0-1" -> true;
                case "1-2" -> true;
                default -> false;
              };

          // Create new node with empty connections
          Node node = new Node(name, latitude, longitude, isStaircase, new ArrayList<>());

          map.put(name, node);
        }
        // Handle the lines between points
        if (line.charAt(1) == 'L') {

        pointsDone = true;

        // Split CSV line into WKT, name, description
        String[] csvParts = line.split(",", 3);
        String description = csvParts.length >= 3
            ? csvParts[2].replace("\"", "").trim()
            : "";

        // Extract numeric coordinates from WKT
        String wantedString = "";
        String wantedCharacters = "-. 0123456789";
        for (int i = 0; i < line.length(); i++) {
          if (wantedCharacters.indexOf(line.charAt(i)) != -1) {
            wantedString += line.charAt(i);
          }
        }

        String[] values = wantedString.strip().split(" ");

        if (values.length < 4) {
          System.out.println("Bad LINE row: " + line);
          return;
        }

        double[] point1 = {
          Double.parseDouble(values[0]),
          Double.parseDouble(values[1])
        };

        double[] point2 = {
          Double.parseDouble(values[2]),
          Double.parseDouble(values[3])
        };

        // If description explicitly defines endpoints, use it
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
  String to   = parts[1].trim();

  Node a = map.get(from);
  Node b = map.get(to);

  if (a != null && b != null) {
    a.connections.add(b.name);
    b.connections.add(a.name);
  }
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
