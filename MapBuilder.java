import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

/*
 * PROGRAM GOALS: 
 * Read data from csv, and turn into a map. (NEEDED)
 * Take map, and find the shortest path between any two points. (NEEDED)
 * Take shortest paths, and use a TSP to solve a given route (NEEDED).
 * Find route based on what shortest paths were. (OPTIONAL BUT EASY)
 * Print to a map to show the route, or some kind of frontend design (OPTIONAL).
 */

class Node {
    String name;
    double latitude;
    double longitude;
    List<String> connections;

    Node(String name, double latitude, double longitude, List<String> connections) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.connections = connections;
    }
    @Override
    public String toString() {
        return name + " [" + latitude + ", " + longitude + "] -> " + connections;
    }
}

public class MapBuilder {

    static final double EARTH_RADIUS_METERS = 6371000.0;
    static final double METERS_TO_FEET = 3.28084;

    public static void main(String[] args) {

        String csvFile = "data.csv";
        Map<String, Node> map = new HashMap<>();

        //fixes the freaky formatting of the point() thing
        Pattern pointPattern = Pattern.compile("POINT \\((-?[0-9.]+) (-?[0-9.]+)\\)");

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {

                if (skipHeader) {
                    skipHeader = false;
                    continue; // skip first line
                }

                List<String> parts = splitCsvLine(line);
                if (parts.size() < 3) {
                    continue;
                }

                String wkt = parts.get(0).trim();
                String name = parts.get(1).trim();
                String connectionText = parts.get(2).trim();
                System.out.println(connectionText);
                
                // find latitude and longitude in WKT
                Matcher m = pointPattern.matcher(wkt);
                double lon = 0;
                double lat = 0;
                if (m.find()) {
                    lon = Double.parseDouble(m.group(1));
                    lat = Double.parseDouble(m.group(2));
                }

                // clean the connection list
                connectionText = connectionText.replace("[", "").replace("]", "");
                String[] connectionArray = connectionText.split(",");
                List<String> connections = new ArrayList<>();
                for (String c : connectionArray) {
                    c = c.trim();
                    if (!c.isEmpty()) {
                        connections.add(c);
                    }
                }

                // make the node and add it to the map
                Node node = new Node(name, lat, lon, connections);
                map.put(name, node);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        // print all nodes
        for (String key : map.keySet()) {
            System.out.println(map.get(key));
        }

        // example test: find distance between two connected nodes
        Node a = map.get("A_B_Stairs_0-1");
        Node b = map.get("Lower_00s_Entrance_A");
        if (a != null && b != null) {
            System.out.println("Distance between " + a.name + " and " + b.name + ": "
                    + distanceFeet(a, b) + " feet");
        }
    }

    // simple function to split a CSV line (handles quotes)
    public static List<String> splitCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        result.add(current.toString());
        return result;
    }

    // calculate distance between two points (in feet)
    public static double distanceFeet(Node a, Node b) {
        double lat1 = Math.toRadians(a.latitude);
        double lon1 = Math.toRadians(a.longitude);
        double lat2 = Math.toRadians(b.latitude);
        double lon2 = Math.toRadians(b.longitude);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
        double distanceMeters = EARTH_RADIUS_METERS * c;

        return distanceMeters * METERS_TO_FEET;
    }
}
