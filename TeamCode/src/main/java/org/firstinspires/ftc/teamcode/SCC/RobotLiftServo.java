package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotLiftServo {
    private CRServo robotLiftServoLeft = null;
    private TouchSensor robotLiftHomeSensorLeft;
    private CRServo robotLiftServoRight = null;
    private TouchSensor robotLiftHomeSensorRight;

    public RobotLiftServo(HardwareMap hardwareMap) {
        // Configure the hardware map
        robotLiftServoLeft = hardwareMap.get(CRServo.class,
                "robotLiftServoLeft");
        robotLiftHomeSensorLeft = hardwareMap.get(TouchSensor.class,
                "robotLiftHomeSensorLeft");
        robotLiftServoRight = hardwareMap.get(CRServo.class,
                "robotLiftServoRight");
        robotLiftHomeSensorRight = hardwareMap.get(TouchSensor.class,
                "robotLiftHomeSensorRight");

        // Configure the servos
        robotLiftServoLeft.setDirection(CRServo.Direction.FORWARD);
        robotLiftServoRight.setDirection(CRServo.Direction.FORWARD);
    }

    public void zero() {
        // Zero the linear actuators
        while (!robotLiftHomeSensorLeft.isPressed()) {
            robotLiftServoLeft.setPower(-0.4);
        }

        // Back off the home switch
        while (robotLiftHomeSensorLeft.isPressed()) {
            robotLiftServoLeft.setPower(0.2);
        }
        robotLiftServoLeft.setPower(0.0);

        // Zero the linear actuator Too
        while (!robotLiftHomeSensorRight.isPressed()) {
            robotLiftServoRight.setPower(-0.4);
        }

        // Back off the home switch
        while (robotLiftHomeSensorRight.isPressed()) {
            robotLiftServoRight.setPower(0.2);
        }
        robotLiftServoRight.setPower(0.0);
    }

    public void run(Gamepad gamepad) {
        // Are we going up and has the upper limit not been reached?
        if (gamepad.right_bumper) {
            // Yes, go up
            robotLiftServoLeft.setPower(1.0);
            robotLiftServoRight.setPower(1.0);
        } else if (gamepad.left_bumper) {
            // No, we are going down
            if (robotLiftHomeSensorLeft.isPressed()) {
                robotLiftServoLeft.setPower(0.0);
            } else {
                robotLiftServoLeft.setPower(-1.0);
            }
            if (robotLiftHomeSensorRight.isPressed()) {
                robotLiftServoRight.setPower(0.0);
            } else {
                robotLiftServoRight.setPower(-1.0);
            }
        } else {
            // No travel requested, stop the servos
            robotLiftServoLeft.setPower(0.0);
            robotLiftServoRight.setPower(0.0);
        }
    }

    public void addTelemetry(Telemetry telemetry) {
    }
}
