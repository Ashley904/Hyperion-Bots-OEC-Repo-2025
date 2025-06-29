package pedroPathing.Core.Paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class FiveSpec {

    // --- Main Points ---
    public static Pose startingPose = new Pose(8, 75, Math.toRadians(0));

    public static Pose scorePreloadSpecimenPose = new Pose(40, 76.5, Math.toRadians(0));

    public static Pose grab2ndSpecimenPose = new Pose(10, 25, Math.toRadians(0));
    public static Pose grab3rdSpecimenPose = new Pose(10, 32, Math.toRadians(0));
    public static Pose grab4thSpecimenPose = new Pose(10, 32, Math.toRadians(0));
    public static Pose grab5thSpecimenPose = new Pose(10, 32, Math.toRadians(0));

    public static Pose score2ndSpecimenPose = new Pose(40, 75, Math.toRadians(0));
    public static Pose score3rdSpecimenPose = new Pose(40, 73.5, Math.toRadians(0));
    public static Pose score4thSpecimenPose = new Pose(40, 72, Math.toRadians(0));
    public static Pose score5thSpecimenPose = new Pose(40, 70, Math.toRadians(0));

    public static Pose parkPose = new Pose(10, 25, Math.toRadians(0));





    // --- Scoring 1st Specimen(Preload) ---
    public PathChain scorePreload(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(startingPose),
                                new Point(scorePreloadSpecimenPose)
                        )
                )
                .setConstantHeadingInterpolation(scorePreloadSpecimenPose.getHeading())
                .setZeroPowerAccelerationMultiplier(4.5)
                .build();
    }





    // --- Pushing 3 Spike Mark Samples ---
    public PathChain push1stSample(){

        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(40, 76.5, Point.CARTESIAN),
                                new Point(27, 40, Point.CARTESIAN),
                                new Point(45, 36, Point.CARTESIAN)
                        )
                )
                .addPath(
                        new BezierCurve(
                                new Point(45, 36, Point.CARTESIAN),
                                new Point(56.5, 34, Point.CARTESIAN),
                                new Point(60, 23, Point.CARTESIAN)
                        )
                )
                .addPath(
                        new BezierLine(
                                new Point(60, 23, Point.CARTESIAN),
                                new Point(20, 23, Point.CARTESIAN)
                        )
                )
                .setZeroPowerAccelerationMultiplier(8)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
    public PathChain push2ndSample(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(20, 23, Point.CARTESIAN),
                                new Point(42, 23, Point.CARTESIAN)
                        )
                )
                .addPath(
                        new BezierCurve(
                                new Point(42, 23, Point.CARTESIAN),
                                new Point(60,23, Point.CARTESIAN),
                                new Point(60, 13, Point.CARTESIAN)
                        )
                )
                .addPath(
                        new BezierLine(
                                new Point(60, 13, Point.CARTESIAN),
                                new Point(20, 13, Point.CARTESIAN)
                        )
                )
                .setZeroPowerAccelerationMultiplier(8)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
    public PathChain push3rdSample(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(20, 13, Point.CARTESIAN),
                                new Point(42, 13, Point.CARTESIAN)
                        )
                )
                .addPath(
                        new BezierCurve(
                                new Point(42, 13, Point.CARTESIAN),
                                new Point(55,14, Point.CARTESIAN),
                                new Point(60, 7, Point.CARTESIAN)
                        )
                )
                .addPath(
                        new BezierLine(
                                new Point(60, 7, Point.CARTESIAN),
                                new Point(20, 7, Point.CARTESIAN)
                        )
                )
                .setZeroPowerAccelerationMultiplier(8)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }





    // --- Collecting + Scoring 2nd Specimen ---
    public PathChain collect2ndSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(20,7, Point.CARTESIAN),
                                new Point(23, 20, Point.CARTESIAN),
                                new Point(15, 25, Point.CARTESIAN)
                        )
                ).setZeroPowerAccelerationMultiplier(4)
                .addPath(
                        new BezierLine(
                                new Point(15,25, Point.CARTESIAN),
                                new Point(grab2ndSpecimenPose)
                        )
                ).setZeroPowerAccelerationMultiplier(2)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
    public PathChain score2ndSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(grab2ndSpecimenPose),
                                new Point(score2ndSpecimenPose)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(8)
                .build();
    }





    // --- Collecting + Scoring 3rd Specimen ---
    public PathChain collect3rdSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score2ndSpecimenPose),
                                new Point(30, 40, Point.CARTESIAN),
                                new Point(15, 32, Point.CARTESIAN)
                        )
                ).setZeroPowerAccelerationMultiplier(8)
                .addPath(
                        new BezierLine(
                                new Point(15, 32, Point.CARTESIAN),
                                new Point(grab3rdSpecimenPose)
                        )
                ).setZeroPowerAccelerationMultiplier(2)
                .setConstantHeadingInterpolation(Math.toRadians((0)))
                .build();
    }
    private PathChain score3rdSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(10,32, Point.CARTESIAN),
                                new Point(score3rdSpecimenPose)
                        )
                )
                .setZeroPowerAccelerationMultiplier(8)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }





    // --- Collecting + Scoring 4th Specimen
    public PathChain collect4thSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score3rdSpecimenPose),
                                new Point(30, 40, Point.CARTESIAN),
                                new Point(15, 32, Point.CARTESIAN)
                        )
                ).setZeroPowerAccelerationMultiplier(8)
                .addPath(
                        new BezierLine(
                                new Point(15, 32, Point.CARTESIAN),
                                new Point(grab4thSpecimenPose)
                        )
                ).setZeroPowerAccelerationMultiplier(2)
                .setConstantHeadingInterpolation(Math.toRadians((0)))
                .build();
    }
    private PathChain score4thSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(10,32, Point.CARTESIAN),
                                new Point(score4thSpecimenPose)
                        )
                )
                .setZeroPowerAccelerationMultiplier(8)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }





    // --- Collecting + Scoring 4th Specimen
    public PathChain collect5thSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(score5thSpecimenPose),
                                new Point(30, 40, Point.CARTESIAN),
                                new Point(15, 32, Point.CARTESIAN)
                        )
                ).setZeroPowerAccelerationMultiplier(8)
                .addPath(
                        new BezierLine(
                                new Point(15, 32, Point.CARTESIAN),
                                new Point(grab5thSpecimenPose)
                        )
                ).setZeroPowerAccelerationMultiplier(2)
                .setConstantHeadingInterpolation(Math.toRadians((0)))
                .build();
    }
    private PathChain score5thSpecimen(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(10,32, Point.CARTESIAN),
                                new Point(score5thSpecimenPose)
                        )
                )
                .setZeroPowerAccelerationMultiplier(8)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }





    // --- Park In Observation Zone ---
    private PathChain park(){

        return new PathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(score5thSpecimenPose),
                                new Point(parkPose)
                        )
                )
                .setZeroPowerAccelerationMultiplier(10)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
}
