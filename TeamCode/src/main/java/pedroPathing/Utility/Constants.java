package pedroPathing.Utility;

public class Constants {


    // Slide Constants
    public double depositSlideKSQ = 0.0;
    public double depositSlideKd = 0.0;
    public double depositSlideKf = 0.0;


    // Robot Constants
    public double minimumDtSpeed = 0.25;
    public double maximumDtSpeed = 1.0;
    public double rotationalKSQ = 0.0;
    public double rotationalKd = 0.0;


    // Slide Motor Constants
    public double depositSlidesTicksInDegrees = 3.96;



    public void setSlideConstants(double depositSlideKSQ, double depositSlideKd, double depositSlideKf){

        this.depositSlideKSQ = depositSlideKSQ;
        this.depositSlideKd = depositSlideKd;
        this.depositSlideKf = depositSlideKf;

    }

    public void setRobotConstants(double minimumDtSpeed, double maximumDtSpeed, double rotationalKSQ, double rotationalKd){

        this.minimumDtSpeed = minimumDtSpeed;
        this.maximumDtSpeed = maximumDtSpeed;
        this.rotationalKSQ = rotationalKSQ;
        this.rotationalKd = rotationalKd;

    }
}
