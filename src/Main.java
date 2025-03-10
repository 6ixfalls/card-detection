import processing.core.PApplet;
import processing.core.PImage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    DImage image;
    private ArrayList<Point> points;
    public void settings() {
        image = new DImage(loadImage("imgs/20250307_092431.jpg"));
        image = Downsample.processImage(image, 3);
        size(image.getWidth(), image.getHeight());
    }
    public void setup() {
        points = new ArrayList<>();
        background(255);
        stroke(0);

        short[][] grid = image.getBWPixelGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                short b = grid[i][j];
                if (b > 200) {
                    points.add(new Point(j, i));
                }
            }
        }
        ArrayList<ArrayList<Point>> clusters = null;
        boolean yay = false;
        while (!yay) {
            try {
                clusters = Localization.kMeansCluster(12, points, image.getWidth(), image.getHeight());
                yay = true;
            } catch (Exception e) {}
        }

        strokeWeight(3);
        colorMode(HSB, clusters.size(), 100, 100);
        for (int i = 0; i < clusters.size(); i++) {
            ArrayList<Point> cluster = clusters.get(i);
            stroke(i, 100, 100);
            for (Point p : cluster) {
                point(p.x, p.y);
            }
        }
    }

    public void draw() {
        //image(image.getPImage(), 0, 0);
    }
}
