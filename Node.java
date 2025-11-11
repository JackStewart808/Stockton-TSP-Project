import java.util.*;

public class Node {
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