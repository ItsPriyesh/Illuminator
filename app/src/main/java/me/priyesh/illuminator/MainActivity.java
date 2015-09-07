package me.priyesh.illuminator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Illuminator";
    private static final String TEST_IMAGE_PATH = "/storage/emulated/0/Download/detect-simple-shapes-feat-img1.png";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) doShit();
            else super.onManagerConnected(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug())
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        else mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }

    private void doShit() {
        Log.d(TAG, "About to do shit");

        final Mat image = Imgcodecs.imread(TEST_IMAGE_PATH);
        final int triangles = TriangleFinder.findTriangles(image);

        Toast.makeText(this, "Triangles Found: " + triangles, Toast.LENGTH_SHORT).show();
    }
}
