package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ConveyorAssembly {

    private CRServo inTakeLeft;

    private CRServo inTakeRight;

    private CRServo leftInside;

    private CRServo rightInside;

    private Servo leftLauncher;

    private Servo rightLauncher;




    public void inTakeOn(){
        inTakeLeft.setPower(0.5);
        inTakeRight.setPower(0.5);
    }

    public void conveyorBeltOn(){
        leftInside.setPower(0.5);
        rightInside.setPower(0.5);
    }

    public void inTakeOff(){
        inTakeLeft.setPower(0.0);
        inTakeRight.setPower(0.0);
    }

    public void conveyorBeltOff(){
        leftInside.setPower(0.0);
        rightInside.setPower(0.0);
    }

    public void launcherToHomePos(){
        leftLauncher.setPosition(150);
        rightLauncher.setPosition(150);
    }

    public void launcherToLaunchPos(){
        leftLauncher.setPosition(250);
        rightLauncher.setPosition(250);
    }

    public void launcherToIntakePos(){
        leftLauncher.setPosition(50);
        rightLauncher.setPosition(50);
    }

    public ConveyorAssembly(HardwareMap hardwareMap) {
        wristPitchServo = hardwareMap.get(Servo.class, "wristPitchServo");
        wristPitchServo.setDirection(Servo.Direction.REVERSE);
    }
    public void run(Gamepad gamepad, int currentSlideEncoderPos) {
        if (gamepad.a) {
            launcherToIntakePos();
        } else if (gamepad.x) {
            launcherToHomePos();
        } else if (gamepad.y) {
            launcherToLaunchPos();
        }

    }

    public void addTelemetry(Telemetry telemetry) {
        //telemetry.addData("currentWristServoPos",  "%7d", currentWristServoPos);
        //telemetry.addData("wristRotationServo", "%7d", wristRotationServo.getPosition());
    }

    public void zero() {
        inTakeRight.setDirection(CRServo.Direction.REVERSE);
        rightInside.setDirection(CRServo.Direction.REVERSE);
    }

}