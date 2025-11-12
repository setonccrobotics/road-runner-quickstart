package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class McWinnerConveyor {
    private CRServo inTakeLeft;

    private CRServo inTakeRight;

    private CRServo outTakeRight;

    private CRServo outTakeLeft;
    private Servo sweepLeft;
    private Servo sweepRight;

    private DcMotorEx launcherMotor;

    private boolean launchToggle = false;
    private ElapsedTime launchToggleTimer = new ElapsedTime();

    private double launchVelocity = 1600;

    public McWinnerConveyor(HardwareMap hardwareMap) {
        // Configure the hardware map
        inTakeLeft = hardwareMap.get(CRServo.class,
                "intakeLeft");
        inTakeRight = hardwareMap.get(CRServo.class,
                "inTakeRight");
        outTakeRight = hardwareMap.get(CRServo.class,
                "outTakeRight");
        outTakeLeft = hardwareMap.get(CRServo.class,
                "outTakeLeft");
        sweepLeft = hardwareMap.get(Servo.class,
                "sweepLeft");
        sweepRight = hardwareMap.get(Servo.class,
                "sweepRight");
        launcherMotor = hardwareMap.get(DcMotorEx.class,
                "launcherMotor");

        // Configure the motor
        outTakeLeft.setDirection(CRServo.Direction.REVERSE);
        outTakeRight.setDirection(CRServo.Direction.FORWARD);
        inTakeLeft.setDirection(CRServo.Direction.FORWARD);
        inTakeRight.setDirection(CRServo.Direction.REVERSE);
        sweepLeft.setDirection(Servo.Direction.FORWARD);
        sweepRight.setDirection(Servo.Direction.REVERSE);
        launcherMotor.setDirection(DcMotor.Direction.FORWARD);

        launchToggleTimer.reset();
    }

    public void zero() {
        sweepLeft.setPosition(0.5);
        sweepRight.setPosition(0.5);
    }

    public void run(Gamepad gamepad, RobotVision robotVision) {
        // Are we going up and has the upper limit not been reached?
        if (gamepad.b){
            inTakeLeft.setPower(1.0);
            inTakeRight.setPower(1.0);
        } else {
            inTakeLeft.setPower(0.0);
            inTakeRight.setPower(0.0);
        }

        if (gamepad.a){
            outTakeLeft.setPower(1.0);
            outTakeRight.setPower(1.0);
        } else {
            outTakeLeft.setPower(0.0);
            outTakeRight.setPower(0.0);
        }

        if (gamepad.y){
            sweepLeft.setPosition(0.3);
            sweepRight.setPosition(0.3);
        }

        if (gamepad.x){
            sweepLeft.setPosition(0.7);
            sweepRight.setPosition(0.7);
        }

        if (gamepad.right_bumper && launchToggleTimer.milliseconds() > 1000) {
            launchToggle = !launchToggle;
            launchToggleTimer.reset();
        }

        if (gamepad.dpad_up)
            launchVelocity += 4.0;
        else if (gamepad.dpad_down)
            launchVelocity -= 4.0;

        if (launchToggle){
          //launcherMotor.setPower(((robotVision.getDistance() * 0.28)/100) + 0.7);
            launcherMotor.setVelocity(launchVelocity);
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("launchVelocity", "%.2f", launchVelocity);
        //telemetry.update();
    }
}
