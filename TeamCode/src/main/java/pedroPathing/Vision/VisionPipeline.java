package pedroPathing.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class VisionPipeline extends OpenCvPipeline {

    @Override
    public Mat processFrame(Mat input) {

        // 1. Finding all of the blue samples with HSV
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_BGR2HSV);

        Scalar lowerBlueBound = new Scalar(0, 100, 100);
        Scalar upperBlueBound = new Scalar(50, 255, 255);


        Mat blueMat = new Mat();
        Core.inRange(hsvMat, lowerBlueBound, upperBlueBound, blueMat);

        // 2. Noise Reduction
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
        Imgproc.morphologyEx(blueMat, blueMat, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(blueMat, blueMat, Imgproc.MORPH_CLOSE, kernel);

        // 3. Performing canny edge detection on the samples before making contours on the samples

        Mat cannyEdgeMat = new Mat();
        Imgproc.Canny(blueMat, cannyEdgeMat, 100, 200);


        // 4. Finding contours of the blue samples only
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyEdgeMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(input, contours, -1, new Scalar(0, 255, 0));

        Imgproc.putText(input, "Pipeline Running...", new Point(170, 25), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);


        return input;

    }
}
