package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotConveyor {
    private CRServo inTakeLeft;

    private CRServo inTakeRight;

    private CRServo outTakeRight;

    private CRServo outTakeLeft;
    private Servo sweepLeft;
    private Servo sweepRight;

    private DcMotorEx launcherMotor;

    private DistanceSensor intakeDistanceSensor;
    private DistanceSensor outtakeTopDistanceSensor;
    private DistanceSensor outtakeSideDistanceSensor;
    private DistanceSensor launchDistanceSensor;


    private boolean launchToggle = false;
    private ElapsedTime launchToggleTimer = new ElapsedTime();
    private boolean launchButtonToggle = false;
    private ElapsedTime launchButtonToggleTimer = new ElapsedTime();
    private double launchVelocity = 1900;

    private boolean launchInProcess = false;

    private double currentTargetDistance = 48.0;

    public RobotConveyor(HardwareMap hardwareMap) {
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

        intakeDistanceSensor = hardwareMap.get(DistanceSensor.class,
                "intakeDistanceSensor");
        outtakeTopDistanceSensor = hardwareMap.get(DistanceSensor.class,
                "outtakeTopDistanceSensor");
        outtakeSideDistanceSensor = hardwareMap.get(DistanceSensor.class,
                "outtakeSideDistanceSensor");
        launchDistanceSensor = hardwareMap.get(DistanceSensor.class,
                "launchDistanceSensor");

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
        sweepLeft.setPosition(0.7);
        sweepRight.setPosition(0.7);
    }

    public void run(Gamepad gamepad, RobotVision robotVision) {
        // Are we going up and has the upper limit not been reached?
        if (gamepad.y){
            inTakeLeft.setPower(1.0);
            inTakeRight.setPower(1.0);
        } else if (!gamepad.a) {
            inTakeLeft.setPower(0.0);
            inTakeRight.setPower(0.0);
        }

        if (gamepad.b){
            outTakeLeft.setPower(1.0);
            outTakeRight.setPower(1.0);
        } else if (!gamepad.a) {
            outTakeLeft.setPower(0.0);
            outTakeRight.setPower(0.0);
        }

        if (gamepad.a) {
            ballPickup();
        }

        if (gamepad.right_bumper && launchButtonToggleTimer.milliseconds() > 500) {
            launchButtonToggle = !launchButtonToggle;
            launchButtonToggleTimer.reset();
        }

        if (launchButtonToggle){
            sweepLeft.setPosition(0.3);
            sweepRight.setPosition(0.3);
        } else {
            sweepLeft.setPosition(0.7);
            sweepRight.setPosition(0.7);
        }

        /*
        if (gamepad.y && launchButtonToggleTimer.milliseconds() > 500) {
            launchButtonToggle = !launchButtonToggle;
            launchButtonToggleTimer.reset();
        } else if (launchInProcess && launchButtonToggleTimer.milliseconds() > 500) {
            launchInProcess = false;
            launchButtonToggle = !launchButtonToggle;
            launchButtonToggleTimer.reset();
        }

        if (launchButtonToggle){
            launchInProcess = true;
            sweepLeft.setPosition(0.3);
            sweepRight.setPosition(0.3);
        } else {
            sweepLeft.setPosition(0.7);
            sweepRight.setPosition(0.7);
        }
*/
        if (gamepad.x && launchToggleTimer.milliseconds() > 1000) {
            launchToggle = !launchToggle;
            launchToggleTimer.reset();
        }

        if (launchToggle) {
            double targetDistance = robotVision.getDistance();
            if (targetDistance > 0.0) {
                launchVelocity = ((targetDistance * 9.4)) + 1425;
            }
            launcherMotor.setVelocity(launchVelocity);
        } else {
            launcherMotor.setVelocity(0.0);
        }
    }

    public void turnInTakeOn() {
        inTakeLeft.setPower(1.0);
        inTakeRight.setPower(1.0);
    }
    public void turnInTakeOff() {
        inTakeLeft.setPower(0.0);
        inTakeRight.setPower(0.0);
    }

    public void turnOutTakeOn() {
        outTakeLeft.setPower(1.0);
        outTakeRight.setPower(1.0);
    }
    public void turnOutTakeOff() {
        outTakeLeft.setPower(0.0);
        outTakeRight.setPower(0.0);
    }

    public void ballPickup() {
        if (launchDistanceSensor.getDistance(DistanceUnit.INCH) > 8.0) {
            turnOutTakeOn();
            turnInTakeOn();
        } else if (getOuttakeSideSensorDistance() > 4.0
                 || getOuttakeTopSensorDistance() > 4.0) {
            turnOutTakeOn();
            turnInTakeOn();
        } else if (getIntakeSensorDistance() > 5.0) {
            turnOutTakeOff();
            turnInTakeOn();
        } else {
            turnOutTakeOff();
            turnInTakeOff();
        }
    }

    public boolean launchBall() {
        sweepLeft.setPosition(0.3);
        sweepRight.setPosition(0.3);
        return true;
    }

    public boolean launchGateOpen() {
        sweepLeft.setPosition(0.7);
        sweepRight.setPosition(0.7);
        return true;
    }

    public void updateTargetDistance(RobotVision robotVision) {
        double targetDistance = robotVision.getDistance();
        if (targetDistance > 0.0) {
            currentTargetDistance = ((targetDistance * 8.582)) + 1494;
        }
    }

    public void updateTargetDistance(double targetDistance) {
        if (targetDistance > 0.0) {
            currentTargetDistance = ((targetDistance * 8.582)) + 1494;
        }
    }

    public void launchMotorOn() {
        launcherMotor.setVelocity(currentTargetDistance);
    }

    public void launchMotorOff() {
        launcherMotor.setVelocity(0.0);
    }

    public double getIntakeSensorDistance() {
        return intakeDistanceSensor.getDistance(DistanceUnit.INCH);
    }

    public double getOuttakeTopSensorDistance() {
        return outtakeTopDistanceSensor.getDistance(DistanceUnit.INCH);
    }

    public double getOuttakeSideSensorDistance() {
        return outtakeSideDistanceSensor.getDistance(DistanceUnit.INCH);
    }

    public double getLaunchSensorDistance() {
        return launchDistanceSensor.getDistance(DistanceUnit.INCH);
    }

    public void addTelemetry(Telemetry telemetry) {
        /*telemetry.addData("launchVelocity", "%.2f", launchVelocity);
        telemetry.addData("intakeDistance", "%.2f", getIntakeSensorDistance());
        telemetry.addData("outtakeTopDistanceSensor", "%.2f", getOuttakeTopSensorDistance());
        telemetry.addData("outtakeSideDistanceSensor", "%.2f", getOuttakeSideSensorDistance());
        telemetry.addData("launchDistanceSensor", "%.2f", getLaunchSensorDistance());*/
        //telemetry.update();
    }
}