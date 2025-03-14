package Filters;

import CardUtil.BoundingBox;
import CardUtil.Downsample;
import CardUtil.Threshold;
import Interfaces.Drawable;
import Interfaces.PixelFilter;
import core.DImage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class CardFilter implements PixelFilter, Drawable {

    @Override
    public DImage processImage(DImage image) {
        image = Downsample.processImage(image, 16);
        image = Threshold.processImage(image, 190);
        return image;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        Mat imageMat = filtered.toMat();
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(imageMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        BoundingBox[] boundingBoxes = new BoundingBox[contours.size()];
        for (int i = 0; i < contours.size(); i++) {
            Point[] points = contours.get(i).toArray();
            int minX = (int)points[0].x;
            int maxX = (int)points[0].x;
            int minY = (int)points[0].y;
            int maxY = (int)points[0].y;
            for (Point p : points) {
                if (p.x < minX) minX = (int) p.x;
                else if (p.x > maxX) maxX = (int) p.x;
                if (p.y < minY) minY = (int) p.y;
                else if (p.y > maxY) maxY = (int) p.y;
            }
            boundingBoxes[i] = new BoundingBox(minX, minY, maxX, maxY);
        }
        ArrayList<BoundingBox> filteredBoxes = getBoundingBoxes(boundingBoxes);
        window.pushStyle();
        window.colorMode(PConstants.RGB);
        window.rectMode(PConstants.CORNERS);
        window.fill(0, 1);
        for (BoundingBox box : filteredBoxes) {
            window.stroke(255, 0, 0);
            window.rect(box.x1, box.y1, box.x2, box.y2);
        }
        window.popStyle();
    }

    private static ArrayList<BoundingBox> getBoundingBoxes(BoundingBox[] boundingBoxes) {
        ArrayList<BoundingBox> filtered = new ArrayList<>();
        for (int i = 0; i < boundingBoxes.length; i++) {
            BoundingBox first = boundingBoxes[i];
            boolean enclosed = false;
            for (int j = 0; j < boundingBoxes.length; j++) {
                if (i == j) continue;
                BoundingBox other = boundingBoxes[j];
                if (other.encloses(first)) {
                    enclosed = true;
                    break;
                }
            }
            if (!enclosed) {
                filtered.add(first);
            }
        }
        return filtered;
    }
}
