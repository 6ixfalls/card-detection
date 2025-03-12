import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import processing.core.PApplet;
import processing.core.PImage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        PApplet.main("Main");
    }

    DImage image;
    public void settings() {
        image = new DImage(loadImage("imgs/20250307_092431.jpg"));
        image = Downsample.processImage(image, 16);
        image = Threshold.processImage(image, 180);
        size(image.getWidth(), image.getHeight());
    }
    public void setup() {
        background(255);
        stroke(30);
        Mat imageMat = image.toMat();
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(imageMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        colorMode(HSB, 360, 100, 100);
        strokeWeight(2);
        for (MatOfPoint contour : contours) {
            System.out.println(Arrays.toString(contour.toArray()));
            beginShape();
            fill((float) (Math.random() * 360), 100, 100);
            for (Point p : contour.toArray()) {
                System.out.print((int)p.x);
                System.out.println(" "+ (int)p.y);
                vertex((int) p.x, (int) p.y);
            }
            endShape(CLOSE);
        }
        System.out.println("done");
    }

    public void draw() {
        //image(image.getPImage(), 0, 0);
    }
}
