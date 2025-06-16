package pedroPathing.Vision;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class VisionPipeline extends OpenCvPipeline {

    public final List<Point> sampleCenters = new ArrayList<>();

    @Override
    public Mat processFrame(Mat input) {
        // Step 1: Blur to reduce noise
        Imgproc.GaussianBlur(input, input, new Size(5, 5), 12);

        // Step 2: Convert to HSV
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_BGR2HSV);

        // Step 3: Apply HSV threshold to isolate blue
        Scalar lowerBlue = new Scalar(10, 100, 100);  // Your original thresholds
        Scalar upperBlue = new Scalar(100, 255, 255);
        Mat blueMask = new Mat();
        Core.inRange(hsvMat, lowerBlue, upperBlue, blueMask);

        // Step 4: Morphological operations to clean mask
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(blueMask, blueMask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(blueMask, blueMask, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.dilate(blueMask, blueMask, kernel); // Optional: enhance structure

        // Step 5: Use Canny edge detection to better separate touching shapes
        Mat edgeMat = new Mat();
        Imgproc.Canny(blueMask, edgeMat, 100, 200);

        // Step 6: Find external contours from edge image
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edgeMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Step 7: Filter and process contours
        sampleCenters.clear();

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area < 500) continue; // Noise filter

            // Optional: shape filter
            Rect rect = Imgproc.boundingRect(contour);
            double aspectRatio = rect.width / (double) rect.height;
            if (aspectRatio < 0.5 || aspectRatio > 2.0) continue;

            // Draw contour
            Imgproc.drawContours(input, List.of(contour), -1, new Scalar(0, 255, 0), 2);

            // Accurate center using RotatedRect
            if (contour.toArray().length >= 5) {
                RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                Point center = rotatedRect.center;
                sampleCenters.add(center);

                Imgproc.circle(input, center, 4, new Scalar(255, 255, 255), -1);
            }
        }

        // Step 8: Debug text
        Imgproc.putText(input, "Pipeline Running...", new Point(170, 25),
                Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);

        return input;
    }
}
