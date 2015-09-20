package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {

    public static void main(String... args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = ImageUtils.readImage("test_image.png");
        final int numTriangles = TriangleFinder.findTriangles(image);
        System.out.print("Found " + numTriangles + " triangles.");
    }
}
