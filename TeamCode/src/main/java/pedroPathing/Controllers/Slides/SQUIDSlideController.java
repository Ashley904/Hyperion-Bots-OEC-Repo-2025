package pedroPathing.Controllers.Slides;
import pedroPathing.Utility.Constants;

public class SQUIDSlideController {

    Constants constants;

    SQUIDSlideController(Constants constants){
        this.constants = constants;
    }

    double lastError = 0.0;

    public double calculate(double error, int currentSlidePosition, double ticksInDegrees){

        double kSQCalculation = Math.sqrt(error * Math.abs(error *  constants.depositSlideKSQ)) * Math.signum(error);
        double derivativeCalculation = (error - lastError) * constants.depositSlideKd;
        double feedforwardCalculation = Math.cos(Math.toRadians(currentSlidePosition / constants.depositSlidesTicksInDegrees)) * constants.depositSlideKf;

        double PIDOutput = kSQCalculation + derivativeCalculation + feedforwardCalculation;

        return Math.max(-1.0, Math.min(1.0, PIDOutput));
    }

}
