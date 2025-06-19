package pedroPathing.Core.Paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class FiveSpec {

    // --- Main Points ---
    public static Pose startingPose = new Pose(0, 0, Math.toRadians(0));

    public static Pose scorePreloadSpecimenPose = new Pose(0, 0, Math.toRadians(0));

    public static Pose grab2ndSpecimenPose = new Pose(0, 0, Math.toRadians(0));
    public static Pose grab3rdSpecimenPose = new Pose(0, 0, Math.toRadians(0));
    public static Pose grab4thSpecimenPose = new Pose(0, 0, Math.toRadians(0));

    public static Pose score2ndSpecimenPose = new Pose(0, 0, Math.toRadians(0));
    public static Pose score3rdSpecimenPose = new Pose(0, 0, Math.toRadians(0));
    public static Pose score4thSpecimenPose = new Pose(0, 0, Math.toRadians(0));
    public static Pose score5thSpecimenPose = new Pose(0, 0, Math.toRadians(0));

    public static Pose partPose = new Pose(0, 0, Math.toRadians(0));


    // --- Scoring 1st Specimen(Preload) ---
    public PathChain scorePreload(){

        return new PathBuilder()
                .addPath(new BezierLine(new Point(startingPose), new Point(scorePreloadSpecimenPose)))
                .setConstantHeadingInterpolation(scorePreloadSpecimenPose.getHeading())
                .setZeroPowerAccelerationMultiplier(4)
                .build();
    }

}
