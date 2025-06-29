package pedroPathing.Vision;

import androidx.core.content.pm.ShortcutInfoCompatSaver;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class VisionPipeline extends OpenCvPipeline {

    public final List<Point> sampleCenters = new ArrayList<>();
    public final List<Double> sampleAngles = new ArrayList<>();

    private final double centerXOfScreen = 480 / 2.0;
    private final int imageHeight = 480; // Adjust if your camera frame height is different

    // Camera configuration (adjust these to your setup)
    private final double cameraMountingAngleDeg = 60; // degrees, downward tilt of camera
    private final double cameraHeightCm = 45; // height of camera lens from ground in centimeters
    private final double verticalFOVDeg = 35.0; // camera vertical field of view in degrees

    public enum VisionMode{ YELLOW, BLUE, RED }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.GaussianBlur(input, input, new Size(5, 5), 15);

        Mat hsvMat = new Mat();
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_BGR2HSV);

        // Yellow Scalar Range
        Scalar lowerYellow = new Scalar(10, 100, 100);
        Scalar upperYellow = new Scalar(100, 255, 255);
        Mat yellowMask = new Mat();
        Core.inRange(hsvMat, lowerYellow, upperYellow, yellowMask);

        // Blue Scalar Range
        Scalar lowerBlue = new Scalar(0, 100, 50);
        Scalar upperBlue = new Scalar(50, 255, 255);
        Mat blueMask = new Mat();
        Core.inRange(hsvMat, lowerBlue, upperBlue, blueMask);

        // Red Scalar Range
        Scalar lowerRed = new Scalar(136, 60, 60);
        Scalar upperRed = new Scalar(255, 155, 162);
        Mat redMask = new Mat();
        Core.inRange(hsvMat, lowerRed, upperRed, redMask);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(blueMask, blueMask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(blueMask, blueMask, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.dilate(blueMask, blueMask, kernel);

        Mat edgeMat = new Mat();
        Imgproc.Canny(blueMask, edgeMat, 100, 200);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edgeMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        sampleCenters.clear();
        sampleAngles.clear();

        Point closestCenter = null;
        MatOfPoint closestContour = null;
        double smallestXDistance = Double.MAX_VALUE;

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area < 500) continue;

            Rect rect = Imgproc.boundingRect(contour);
            double aspectRatio = rect.width / (double) rect.height;
            if (aspectRatio < 0.5 || aspectRatio > 2.0) continue;

            Moments moments = Imgproc.moments(contour);
            if (moments.get_m00() == 0) continue;

            double cX = moments.get_m10() / moments.get_m00();
            double cY = moments.get_m01() / moments.get_m00();
            Point center = new Point(cX, cY);

            sampleCenters.add(center);  // Store all centers

            double xDistanceFromCenter = Math.abs(cX - centerXOfScreen);
            if (xDistanceFromCenter < smallestXDistance) {
                smallestXDistance = xDistanceFromCenter;
                closestCenter = center;
                closestContour = contour;
            }
        }

        if (closestContour != null && closestCenter != null) {
            Imgproc.drawContours(input, List.of(closestContour), -1, new Scalar(0, 255, 0), 2);

            // Compute and annotate angle of sample
            RotatedRect rotated = Imgproc.minAreaRect(new MatOfPoint2f(closestContour.toArray()));
            double angle = rotated.angle;
            if (rotated.size.width < rotated.size.height) {
                angle += 90;
            }
            angle = (angle + 360) % 180;

            sampleAngles.add(angle);

            Imgproc.circle(input, closestCenter, 5, new Scalar(255, 255, 255), -1);
            Imgproc.putText(input, "(" + (int) closestCenter.x + "," + (int) closestCenter.y + ")", new Point(closestCenter.x + 10, closestCenter.y),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 255), 1);
            Imgproc.putText(input, String.format("%.1f deg", angle), new Point(closestCenter.x + 10, closestCenter.y + 15),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.0, new Scalar(255, 255, 0), 1);

            // Calculate distance to sample in cm and display
            double distanceCm = calculateDistanceCm(closestCenter);
            Imgproc.putText(input, String.format("Dist: %.1f cm", distanceCm),
                    new Point(closestCenter.x + 10, closestCenter.y + 30),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.0, new Scalar(255, 255, 0), 2);
        }

        Imgproc.putText(input, "Pipeline Running...", new Point(170, 25),
                Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);

        return input;
    }

    /**
     * Calculate distance from camera to sample on ground plane in centimeters
     * using the vertical pixel position of the sample center,
     * camera mounting angle, camera height, and vertical FOV.
     */
    private double calculateDistanceCm(Point sampleCenter) {
        double centerYOfScreen = imageHeight / 2.0;
        double pixelOffsetY = centerYOfScreen - sampleCenter.y; // y axis inverted: top is 0

        // Convert pixel offset to angle offset (degrees)
        double angleOffsetDeg = (pixelOffsetY / imageHeight) * verticalFOVDeg;

        // Total angle from horizontal to sample (degrees)
        double totalAngleDeg = cameraMountingAngleDeg + angleOffsetDeg;

        // Convert angle to radians for math functions
        double totalAngleRad = Math.toRadians(totalAngleDeg);

        // Prevent division by zero or negative distance if angle too low
        if (totalAngleRad <= 0.0 || totalAngleRad >= Math.PI / 2) {
            return -1; // invalid distance
        }

        // distance = height / tan(angle)
        double distanceCm = cameraHeightCm / Math.tan(totalAngleRad);

        return distanceCm;
    }
}
