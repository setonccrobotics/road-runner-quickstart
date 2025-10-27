package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class McThrowyLauncher {
    private DcMotor launchMotorLeft = null;
    private DcMotor launchMotorRight = null;
    private Servo launchAimServoLeft;
    private Servo launchAimServoRight;
    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   160;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
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

        // Configure the motor
        launchMotorLeft.setDirection(DcMotor.Direction.FORWARD);
        launchMotorRight.setDirection(DcMotor.Direction.REVERSE);
        launchAimServoLeft.setDirection(Servo.Direction.FORWARD);
        launchAimServoRight.setDirection(Servo.Direction.REVERSE);
    }

    public void zero() {
        // Zero the linear actuators
    }

    public void run(Gamepad gamepad) {
        // slew the servo, according to the rampUp (direction) variable.
        if (gamepad.y) {
            // Keep stepping up until we hit the max value.
            position += INCREMENT ;
            if (position >= MAX_POS ) {
                position = MAX_POS;
            }
        }
        else if (gamepad.a) {
            // Keep stepping down until we hit the min value.
            position -= INCREMENT ;
            if (position <= MIN_POS ) {
                position = MIN_POS;
            }
        }

        // Set the servo to the new position and pause;
        launchAimServoRight.setPosition(position);
        //sleep(CYCLE_MS);
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("launchAimServoLeft Pos", "%7d",
                launchAimServoLeft.getPosition());
        telemetry.addData("launchAimServoRight Pos", "%7d",
                launchAimServoRight.getPosition());
    }
}
