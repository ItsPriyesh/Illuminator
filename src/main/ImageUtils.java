package main;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageUtils {

    private static final String IMAGE_PROCESSING_DIR = "./image_processing/";

    public static final String INPUT_DIR = IMAGE_PROCESSING_DIR + "input/";
    public static final String OUTPUT_DIR = IMAGE_PROCESSING_DIR + "output/";
    public static final String INTERMEDIATE_DIR = IMAGE_PROCESSING_DIR + "intermediate/";

    public static void writeImage(final Mat image, final String name, final String directory) {
        Imgcodecs.imwrite(directory + name, image);
    }

    public static Mat readImage(final String name) {
        return Imgcodecs.imread(INPUT_DIR + name);
    }
}
