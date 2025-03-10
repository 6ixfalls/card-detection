import processing.core.PVector;

import java.util.ArrayList;

public class Localization {
    private static void initClusters(ArrayList<ArrayList<Point>> clusters, int k) {
        clusters.clear();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
    }

    public static Point calculateCentroid(ArrayList<Point> cluster) {
        Point sum = new Point(0, 0);
        for (Point p : cluster) {
            sum = sum.add(p);
        }
        return sum.div(cluster.size());
    }

    public static ArrayList<ArrayList<Point>> kMeansCluster(int k, ArrayList<Point> points, int width, int height) {
        Point[] centroids = new Point[k];
        for (int i = 0; i < k; i++) {
            centroids[i] = new Point((int)(Math.random() * width), (int)(Math.random() * height));
        }
        ArrayList<ArrayList<Point>> clusters = new ArrayList<>();

        boolean converged = false;
        while (!converged) {
            initClusters(clusters, k);

            for (Point point : points) {
                float minDist = Integer.MAX_VALUE;
                int minIndex = 0;
                for (int i = 0; i < k; i++) {
                    float dist = point.distSquared(centroids[i]);
                    if (dist < minDist) {
                        minDist = dist;
                        minIndex = i;
                    }
                }
                clusters.get(minIndex).add(point);
            }

            Point[] newCentroids = new Point[k];
            for (int i = 0; i < clusters.size(); i++) {
                newCentroids[i] = calculateCentroid(clusters.get(i));
            }
            for (int i = 0; i < newCentroids.length; i++) {
                if (!centroids[i].equals(newCentroids[i])) break;
                if (i == newCentroids.length - 1) converged = true;
            }
            centroids = newCentroids;
        }

        return clusters;
    }
}
