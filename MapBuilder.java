import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class MapBuilder {

    final private Map<String, Node> map; // holds all nodes

    public MapBuilder() {
        map = new HashMap<>();
    }

    public void buildMap(String csvFile) {
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
                if (parts.size() < 3) continue;

                String wkt = parts.get(0).trim();
                String name = parts.get(1).trim();
                String connectionText = parts.get(2).trim();

                // extract coordinates
                Matcher m = pointPattern.matcher(wkt);
                double lon = 0, lat = 0;
                if (m.find()) {
                    lon = Double.parseDouble(m.group(1));
                    lat = Double.parseDouble(m.group(2));
                }

                // parse connections
                connectionText = connectionText.replace("[", "").replace("]", "");
                String[] connectionArray = connectionText.split(",");
                List<String> connections = new ArrayList<>();
                for (String c : connectionArray) {
                    c = c.trim();
                    if (!c.isEmpty()) {
                        connections.add(c);
                    }
                }

                Node node = new Node(name, lat, lon, connections);
                map.put(name, node);
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    /**
     * Get the node map
     */
    public Map<String, Node> getMap() {
        return map;
    }

    /**
     * Helper function to split a CSV line safely
     */
    private static List<String> splitCsvLine(String line) {
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
}
