import java.util.Map;

public class TestMapBuilder {
    public static void main(String[] args) {
        // create instance
        MapBuilder builder = new MapBuilder();
        builder.buildMap("data.csv"); // load CSV

        // get the map
        Map<String, Node> map = builder.getMap();

        System.out.println("=== FULL NODE MAP ===");
        for (String nodeName : map.keySet()) {
            Node node = map.get(nodeName);
            System.out.println(node);
        }

        // example: calculate distance using DistanceCalculator
        Node a = map.get("A_B_Stairs_0-1");
        Node b = map.get("Lower_00s_Entrance_A");
        if (a != null && b != null) {
            double feet = DistanceCalculator.feet(a, b);
            System.out.println("Distance: " + feet + " feet");
        }
    }
}
