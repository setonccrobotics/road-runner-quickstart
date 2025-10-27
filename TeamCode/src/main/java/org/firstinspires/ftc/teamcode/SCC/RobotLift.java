package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLift {
    static final int    MIN_MOTOR_ENCODER_POS    =   150;
    static final int    MAX_MOTOR_ENCODER_POS    =   12372;

    private DcMotor robotLiftMotorA = null;
    private TouchSensor robotLiftHomeSensorA;
    private DcMotor robotLiftMotorB = null;
    private TouchSensor robotLiftHomeSensorB;

    public RobotLift(HardwareMap hardwareMap) {
        // Configure the hardware map
        robotLiftMotorA = hardwareMap.get(DcMotor.class,
                "robotLiftMotorA");
        robotLiftHomeSensorA = hardwareMap.get(TouchSensor.class,
                "robotLiftHomeSensorA");
        robotLiftMotorB = hardwareMap.get(DcMotor.class,
                "robotLiftMotorB");
        robotLiftHomeSensorB = hardwareMap.get(TouchSensor.class,
                "robotLiftHomeSensorB");

        // Configure the motor
        robotLiftMotorA.setDirection(DcMotor.Direction.FORWARD);
        robotLiftMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robotLiftMotorB.setDirection(DcMotor.Direction.FORWARD);
        robotLiftMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void zero() {
        // Zero the linear actuators
        while (!robotLiftHomeSensorA.isPressed()) {
            robotLiftMotorA.setPower(-0.4);
        }

        // Back off the home switch
        while (robotLiftHomeSensorA.isPressed()) {
            robotLiftMotorA.setPower(0.2);
        }
        robotLiftMotorA.setPower(0.0);

        // Zero the linear actuator Too
        while (!robotLiftHomeSensorB.isPressed()) {
            robotLiftMotorB.setPower(-0.4);
        }

        // Back off the home switch
        while (robotLiftHomeSensorB.isPressed()) {
            robotLiftMotorB.setPower(0.2);
        }
        robotLiftMotorB.setPower(0.0);

        // Reset the encoder zero position
        robotLiftMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robotLiftMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void run(Gamepad gamepad) {
        // Are we going up and has the upper limit not been reached?
        if (gamepad.right_bumper) {
            //if (gamepad.right_bumper
            //        && overheadLiftMotor.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
            if (robotLiftMotorA.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
                // Yes, go up
                robotLiftMotorA.setPower(1.0);
            } else {
                robotLiftMotorA.setPower(0.0);
            }
            if (robotLiftMotorB.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
                // Yes, go up
                robotLiftMotorB.setPower(1.0);
            }  else {
                robotLiftMotorB.setPower(0.0);
            }
        } else if (gamepad.left_bumper) {
                //&& overheadLiftMotor.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                //&& !overheadLiftHomeSensor.isPressed()) {
            if (robotLiftMotorA.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                    && !robotLiftHomeSensorA.isPressed()) {
                // No, we are going down
                robotLiftMotorA.setPower(-1.0);
            } else {
                robotLiftMotorA.setPower(0.0);
            }

            if (robotLiftMotorB.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                    && !robotLiftHomeSensorB.isPressed()) {
                // No, we are going down
                robotLiftMotorB.setPower(-1.0);
            } else {
                robotLiftMotorB.setPower(0.0);
            }
        } else {
            // No travel requested, stop the motors
            robotLiftMotorA.setPower(0.0);
            robotLiftMotorB.setPower(0.0);
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Robot Lift Motor A Pos", "%7d",
                robotLiftMotorA.getCurrentPosition());
        telemetry.addData("Robot Lift Motor B Pos", "%7d",
                robotLiftMotorB.getCurrentPosition());
    }
}
