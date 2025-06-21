package pedroPathing.Core.Robot;

import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.DoubleSupplier;

public class GamepadControls {

    public static class Controls{

        public GamepadEx gamePad1;
        public GamepadEx gamePad2;

        public GamepadButton scoreSpecimen;
        public GamepadButton transferSample;
        public GamepadButton intakeHardReset;
        public GamepadButton outtakeHardReset;

        public GamepadButton rotateIntakeLeft;
        public GamepadButton rotateIntakeRight;

        public DoubleSupplier driveYInput;
        public DoubleSupplier driveXInput;
        public DoubleSupplier collectSpecimen;

        public Controls(Gamepad gamePad1, Gamepad gamePad2){

            this.gamePad1 = new GamepadEx(gamePad1);
            this.gamePad2 = new GamepadEx(gamePad2);

            this.scoreSpecimen = this.gamePad1.getGamepadButton(GamepadKeys.Button.A); //X
            this.transferSample = this.gamePad1.getGamepadButton(GamepadKeys.Button.Y); // Triangle
            this.intakeHardReset = this.gamePad2.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON);
            this.outtakeHardReset = this.gamePad2.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON);
            this.rotateIntakeLeft = this.gamePad2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER);
            this.rotateIntakeRight = this.gamePad2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER);

        }


    }
}
