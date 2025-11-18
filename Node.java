import java.util.*;

public class Node {
    String name;
    double latitude;
    double longitude;
    double floor;
    List<String> connections;

    Node(String name, double latitude, double longitude, double floor, List<String> connections) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.connections = connections;
    }
    @Override
    public String toString() {
        return name + " " + floor + " " + " [" + latitude + ", " + longitude + "] -> " + connections;
    }
}