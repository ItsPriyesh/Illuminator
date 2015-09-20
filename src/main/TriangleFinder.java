package main;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static main.ImageUtils.OUTPUT_DIR;
import static main.ImageUtils.INTERMEDIATE_DIR;
import static main.ImageUtils.writeImage;

public class TriangleFinder {

    private static final Scalar COLOR_RED = new Scalar(0, 0, 255);

    public static int findTriangles(Mat image) {
        int triangles = 0;
        final Mat processed = canny(grayscale(image));
        writeImage(processed, "grayscale_canny.png", INTERMEDIATE_DIR);

        final List<MatOfPoint> contours = getContours(processed);

        MatOfPoint2f approx = new MatOfPoint2f();

        Mat output = image.clone();

        for (MatOfPoint contour : contours) {
            final MatOfPoint2f mat = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(mat, approx, 0.02 * Imgproc.arcLength(mat, true), true);

            if (isTooSmall(contour) || !isConvex(approx)) continue;
            if (getVertices(approx) == 3) {
                drawTriangle(output, approx.toArray());
                triangles++;
            }
        }

        writeImage(output, "output.png", OUTPUT_DIR);
        return triangles;
    }

    private static Mat drawTriangle(Mat mat, Point[] points) {
        Imgproc.line(mat, points[0], points[1], COLOR_RED, 3);
        Imgproc.line(mat, points[1], points[2], COLOR_RED, 3);
        Imgproc.line(mat, points[2], points[0], COLOR_RED, 3);
        return mat;
    }

    private static Mat grayscale(Mat mat) {
        final Mat gray = new Mat();
        Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);
        return gray;
    }

    private static Mat canny(Mat mat) {
        final Mat canny = new Mat();
        Imgproc.Canny(mat, canny, 50, 5);
        return canny;
    }

    private static List<MatOfPoint> getContours(Mat mat) {
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mat.clone(), contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }

    private static final int MIN_CONTOUR_SIZE = 100;

    private static int getVertices(MatOfPoint2f mat) {
        return mat.toArray().length;
    }

    private static boolean isTooSmall(Mat mat) {
        return Math.abs(Imgproc.contourArea(mat)) < MIN_CONTOUR_SIZE;
    }

    private static boolean isConvex(MatOfPoint2f mat) {
        return Imgproc.isContourConvex(new MatOfPoint(mat.toArray()));
    }

}