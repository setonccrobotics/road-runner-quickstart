package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class McThrowyLauncher {
    private DcMotor launchMotorLeft = null;
    private DcMotor launchMotorRight = null;
    private Servo launchAimServoLeft;
    private Servo launchAimServoRight;
    private CRServo conveyorBeltServo;
    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   160;     // period of each cycle
    static final double MAX_POS     =  0.85;     // Maximum position (software limit: 0.85)
    static final double MIN_POS     =  0.25;     // Minimum position (software limit: 0.15)
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position

    public McThrowyLauncher(HardwareMap hardwareMap) {
        // Configure the hardware map
        launchMotorLeft = hardwareMap.get(DcMotor.class,
                "launchMotorLeft");
        launchMotorRight = hardwareMap.get(DcMotor.class,
                "launchMotorRight");

        launchAimServoLeft = hardwareMap.get(Servo.class,
                "launchAimServoLeft");
        launchAimServoRight = hardwareMap.get(Servo.class,
                "launchAimServoRight");

        conveyorBeltServo = hardwareMap.get(CRServo.class,
                "conveyorBeltServo");

        // Configure the motor
        launchMotorLeft.setDirection(DcMotor.Direction.FORWARD);
        launchMotorRight.setDirection(DcMotor.Direction.REVERSE);
        launchAimServoLeft.setDirection(Servo.Direction.FORWARD);
        launchAimServoRight.setDirection(Servo.Direction.FORWARD);
    }

    public void zero() {
        // Zero the linear actuators
        launchAimServoRight.setPosition(MIN_POS);
        launchAimServoLeft.setPosition(MIN_POS);
    }

    public void run(Gamepad gamepad) {
        // slew the servo, according to the rampUp (direction) variable.
        if (gamepad.dpad_up) {
            // Keep stepping up until we hit the max value.
            position += INCREMENT;
            if (position >= MAX_POS) {
                position = MAX_POS;
            }
        } else if (gamepad.dpad_down) {
            // Keep stepping down until we hit the min value.
            position -= INCREMENT;
            if (position <= MIN_POS) {
                position = MIN_POS;
            }
        }

        // Set the servo to the new position and pause;
        launchAimServoRight.setPosition(position);
        launchAimServoLeft.setPosition(position);

        // Turn on the launch motor
        if (gamepad.y) {
            launchMotorLeft.setPower(1.0);
            launchMotorRight.setPower(1.0);
        } else if (gamepad.x) {
            launchMotorLeft.setPower(0.0);
            launchMotorRight.setPower(0.0);
        }

        if (gamepad.right_bumper) {
            conveyorBeltServo.setPower(1.0);
        } else if (gamepad.left_bumper) {
            conveyorBeltServo.setPower(-1.0);
        } else {
            conveyorBeltServo.setPower(0.0);
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        double launchAimServoLeftPos = 0.0;
        launchAimServoLeftPos = launchAimServoLeft.getPosition();
        double launchAimServoRightPos = 0.0;
        launchAimServoRightPos = launchAimServoRight.getPosition();

        telemetry.addData("launchAimServoLeft Pos", "%7f", launchAimServoLeftPos);
        telemetry.addData("launchAimServoRight Pos", "%7f", launchAimServoRightPos);
    }
}
