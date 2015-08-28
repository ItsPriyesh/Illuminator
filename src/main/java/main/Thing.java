package main;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Thing {
    public void run() {
        int trianglesFound = 0;

        Mat image = Highgui.imread(getClass().getResource("/test_image.png").getPath());
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        Mat bw = new Mat();
        Imgproc.Canny(gray, bw, 50, 5);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(bw.clone(), contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint2f approx = new MatOfPoint2f();
        Mat dst = image.clone();

        for (MatOfPoint contour : contours) {
            MatOfPoint2f mat = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(mat, approx, 0.02 * Imgproc.arcLength(mat, true), true);

            if (Math.abs(Imgproc.contourArea(contour)) < 100 || !Imgproc.isContourConvex(new MatOfPoint(approx.toArray()))) {
                continue;
            }

            if (approx.toArray().length == 3) {
                drawCircle(dst, new Point(contour.toArray()[0].x, contour.toArray()[0].y));
                trianglesFound++;
            }
        }

        System.out.print("Found " + trianglesFound + " triangles.");
    }

    private void drawCircle(Mat dst, Point point) {
        Core.circle(dst, point, 5, new Scalar(208, 221, 10));
        writeImage("circled.png", dst);
    }

    private static final String SAVE_LOCATION = "/Users/priyeshpatel/Desktop/Illuminator/";

    private static void writeImage(String filename, Mat image) {
        Highgui.imwrite(SAVE_LOCATION + filename, image);
    }
}
