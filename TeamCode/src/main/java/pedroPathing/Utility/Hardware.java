package pedroPathing.Utility;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware {

    DcMotor front_left_motor = null;
    DcMotor back_left_motor = null;
    DcMotor front_right_motor = null;
    DcMotor back_right_motor = null;

    HardwareMap hardwareMap;

    public Hardware(HardwareMap hardwareMap){
        initialize(hardwareMap);
    }

    private void initialize(HardwareMap hardwareMap){

        this.hardwareMap = hardwareMap;

        // ToDo -> Motor Initialization: 1. Change Motors names, 2. Reverse Correct Motors
        front_left_motor = hardwareMap.get(DcMotor.class, "FL");
        back_left_motor = hardwareMap.get(DcMotor.class, "BL");
        front_right_motor = hardwareMap.get(DcMotor.class, "FR");
        back_right_motor = hardwareMap.get(DcMotor.class, "BR");

        front_left_motor.setDirection(DcMotor.Direction.FORWARD);
        back_left_motor.setDirection(DcMotor.Direction.FORWARD);
        front_right_motor.setDirection(DcMotor.Direction.FORWARD);
        back_right_motor.setDirection(DcMotor.Direction.FORWARD);

        front_left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_left_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_right_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}