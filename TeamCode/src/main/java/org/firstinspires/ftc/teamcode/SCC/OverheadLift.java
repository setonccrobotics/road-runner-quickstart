package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class OverheadLift {
    static final int    MIN_MOTOR_ENCODER_POS    =   150;
    static final int    MAX_MOTOR_ENCODER_POS    =   12372;

    private DcMotor overheadLiftMotor = null;
    private TouchSensor overheadLiftHomeSensor;
    private DcMotor overheadLiftMotorToo = null;
    private TouchSensor overheadLiftHomeSensorToo;

    public OverheadLift(HardwareMap hardwareMap) {
        // Configure the hardware map
        overheadLiftMotor  = hardwareMap.get(DcMotor.class,
                "overheadLiftMotor");
        overheadLiftHomeSensor = hardwareMap.get(TouchSensor.class,
                "overheadLiftHomeSensor");
        overheadLiftMotorToo  = hardwareMap.get(DcMotor.class,
                "overheadLiftMotorToo");
        overheadLiftHomeSensorToo = hardwareMap.get(TouchSensor.class,
                "overheadLiftHomeSensorToo");

        // Configure the motor
        overheadLiftMotor.setDirection(DcMotor.Direction.FORWARD);
        overheadLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        overheadLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        overheadLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        overheadLiftMotorToo.setDirection(DcMotor.Direction.FORWARD);
        overheadLiftMotorToo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        overheadLiftMotorToo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        overheadLiftMotorToo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void zero() {
        // Zero the linear actuators
        while (!overheadLiftHomeSensor.isPressed()) {
            overheadLiftMotor.setPower(-0.4);
        }

        // Back off the home switch
        while (overheadLiftHomeSensor.isPressed()) {
            overheadLiftMotor.setPower(0.2);
        }
        overheadLiftMotor.setPower(0.0);

        // Zero the linear actuator Too
        while (!overheadLiftHomeSensorToo.isPressed()) {
            overheadLiftMotorToo.setPower(-0.4);
        }

        // Back off the home switch
        while (overheadLiftHomeSensorToo.isPressed()) {
            overheadLiftMotorToo.setPower(0.2);
        }
        overheadLiftMotorToo.setPower(0.0);

        // Reset the encoder zero position
        overheadLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        overheadLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        overheadLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        overheadLiftMotorToo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        overheadLiftMotorToo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        overheadLiftMotorToo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void run(Gamepad gamepad) {
        // Are we going up and has the upper limit not been reached?
        if (gamepad.right_bumper) {
            //if (gamepad.right_bumper
            //        && overheadLiftMotor.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
            if (overheadLiftMotor.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
                // Yes, go up
                overheadLiftMotor.setPower(1.0);
            } else {
                overheadLiftMotor.setPower(0.0);
            }
            if (overheadLiftMotorToo.getCurrentPosition() <= MAX_MOTOR_ENCODER_POS) {
                // Yes, go up
                overheadLiftMotorToo.setPower(1.0);
            }  else {
                overheadLiftMotorToo.setPower(0.0);
            }
        } else if (gamepad.left_bumper) {
                //&& overheadLiftMotor.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                //&& !overheadLiftHomeSensor.isPressed()) {
            if (overheadLiftMotor.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                    && !overheadLiftHomeSensor.isPressed()) {
                // No, we are going down
                overheadLiftMotor.setPower(-1.0);
            } else {
                overheadLiftMotor.setPower(0.0);
            }

            if (overheadLiftMotorToo.getCurrentPosition() >= MIN_MOTOR_ENCODER_POS
                    && !overheadLiftHomeSensorToo.isPressed()) {
                // No, we are going down
                overheadLiftMotorToo.setPower(-1.0);
            } else {
                overheadLiftMotorToo.setPower(0.0);
            }
        } else {
            // No travel requested, stop the motors
            overheadLiftMotor.setPower(0.0);
            overheadLiftMotorToo.setPower(0.0);
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Overhead Lift Motor Pos", "%7d",
                overheadLiftMotor.getCurrentPosition());
        telemetry.addData("Overhead Lift Motor Too Pos", "%7d",
                overheadLiftMotorToo.getCurrentPosition());
    }
}
