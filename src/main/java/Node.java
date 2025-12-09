import java.util.*;

public class Node {
  String name;
  double latitude;
  double longitude;
  boolean isStaircase;
  List<String> connections;

  Node(
      String name,
      double latitude,
      double longitude,
      boolean isStaircase,
      List<String> connections) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.isStaircase = isStaircase;
    this.connections = connections;
  }

  Node(double[] coordinates) {
    this.latitude = coordinates[0];
    this.longitude = coordinates[1];
  }

  @Override
  public String toString() {
    return name
        + " "
        + isStaircase
        + " "
        + " ["
        + latitude
        + ", "
        + longitude
        + "] -> "
        + connections;
  }
}
