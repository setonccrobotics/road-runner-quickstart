package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLift {
    static final int    MIN_MOTOR_ENCODER_POS    =   150;
    static final int    MAX_MOTOR_ENCODER_POS    =   12372;

    private DcMotor robotLiftMotorLeft = null;
    private TouchSensor robotLiftHomeSensorLeft;
    private DcMotor robotLiftMotorRight = null;
    private TouchSensor robotLiftHomeSensorRight;

    private int previousLiftMotorLeftPos = 0;
    private int previousLiftMotorRightPos = 0;

    public RobotLift(HardwareMap hardwareMap) {
        // Configure the hardware map
        robotLiftMotorLeft = hardwareMap.get(DcMotor.class,
                "robotLiftMotorLeft");
        robotLiftHomeSensorLeft = hardwareMap.get(TouchSensor.class,
                "robotLiftHomeSensorLeft");
        robotLiftMotorRight = hardwareMap.get(DcMotor.class,
                "robotLiftMotorRight");
        robotLiftHomeSensorRight = hardwareMap.get(TouchSensor.class,
                "robotLiftHomeSensorRight");

        // Configure the motor
        robotLiftMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        robotLiftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robotLiftMotorRight.setDirection(DcMotor.Direction.FORWARD);
        robotLiftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void zero() {
        // Zero the linear actuators
        while (!robotLiftHomeSensorLeft.isPressed()) {
            robotLiftMotorLeft.setPower(-0.4);
        }

        // Back off the home switch
        while (robotLiftHomeSensorLeft.isPressed()) {
            robotLiftMotorLeft.setPower(0.2);
        }
        robotLiftMotorLeft.setPower(0.0);

        // Zero the linear actuator Too
        while (!robotLiftHomeSensorRight.isPressed()) {
            robotLiftMotorRight.setPower(-0.4);
        }

        // Back off the home switch
        while (robotLiftHomeSensorRight.isPressed()) {
            robotLiftMotorRight.setPower(0.2);
        }
        robotLiftMotorRight.setPower(0.0);

        // Reset the encoder zero position
        robotLiftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robotLiftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLiftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robotLiftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void run(Gamepad gamepad) {
        // Are we going up and has the upper limit not been reached?
        if (gamepad.right_bumper) {
            robotLiftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robotLiftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            if (robotLiftMotorLeft.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
                // Yes, go up
                robotLiftMotorLeft.setPower(1.0);
            } else {
                robotLiftMotorLeft.setPower(0.0);
            }
            if (robotLiftMotorRight.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
                // Yes, go up
                robotLiftMotorRight.setPower(1.0);
            }  else {
                robotLiftMotorRight.setPower(0.0);
            }

            previousLiftMotorLeftPos = robotLiftMotorLeft.getCurrentPosition();
            previousLiftMotorRightPos = robotLiftMotorRight.getCurrentPosition();
        } else if (gamepad.left_bumper) {
            robotLiftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robotLiftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            if (robotLiftMotorLeft.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                    && !robotLiftHomeSensorLeft.isPressed()) {
                // No, we are going down
                robotLiftMotorLeft.setPower(-1.0);
            } else {
                robotLiftMotorLeft.setPower(0.0);
            }

            if (robotLiftMotorRight.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                    && !robotLiftHomeSensorRight.isPressed()) {
                // No, we are going down
                robotLiftMotorRight.setPower(-1.0);
            } else {
                robotLiftMotorRight.setPower(0.0);
            }

            previousLiftMotorLeftPos = robotLiftMotorLeft.getCurrentPosition();
            previousLiftMotorRightPos = robotLiftMotorRight.getCurrentPosition();
        } else {
            // No travel requested, stop the motors
            //robotLiftMotorLeft.setPower(0.0);
            //robotLiftMotorRight.setPower(0.0);
            robotLiftMotorLeft.setTargetPosition(previousLiftMotorLeftPos);
            robotLiftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robotLiftMotorLeft.setPower(-0.3);
            robotLiftMotorRight.setTargetPosition(previousLiftMotorRightPos);
            robotLiftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robotLiftMotorRight.setPower(-0.3);
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Robot Lift Motor A Pos", "%7d",
                robotLiftMotorLeft.getCurrentPosition());
        telemetry.addData("Robot Lift Motor B Pos", "%7d",
                robotLiftMotorRight.getCurrentPosition());
    }
}
