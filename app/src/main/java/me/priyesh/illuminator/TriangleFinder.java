package me.priyesh.illuminator;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class TriangleFinder {

    public static int findTriangles(Mat image) {
        int triangles = 0;
        final Mat processed = canny(grayscale(image));
        final List<MatOfPoint> contours = getContours(processed);

        MatOfPoint2f approx = new MatOfPoint2f();

        for (MatOfPoint contour : contours) {
            final MatOfPoint2f mat = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(mat, approx, 0.02 * Imgproc.arcLength(mat, true), true);

            if (isTooSmall(contour) || !isConvex(approx)) continue;
            if (getVertices(approx) == 3) triangles++;
        }

        return triangles;
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
