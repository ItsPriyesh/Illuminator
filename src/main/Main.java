package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {

    private static final String TEST_IMAGE_PATH = "/Users/priyeshpatel/Desktop/test_image.png";

    public static void main(String... args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = Imgcodecs.imread(TEST_IMAGE_PATH);
        final int numTriangles = TriangleFinder.findTriangles(image);
        System.out.print("Found " + numTriangles + " triangles.");
    }
}
