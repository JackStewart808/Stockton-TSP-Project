public class DistanceCalculator
{
    static final double EARTH_RADIUS_METERS = 6371000.0;
    static final double METERS_TO_FEET = 3.28084;

    public static double feet(Node a, Node b) {
        boolean validLocationPair = false;
        for(String location : a.connections) {
            System.out.println(location);
            if(location.equals(b.name)) {
                validLocationPair = true;
            }
        }
        if(!validLocationPair) {
            return -1;
        }

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
