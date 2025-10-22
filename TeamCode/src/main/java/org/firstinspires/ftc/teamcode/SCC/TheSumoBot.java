package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name="TheSumoBot", group="SCC")
@Disabled
public class TheSumoBot extends LinearOpMode {
    public DistanceSensor distanceSensor;
    public double currentDistance;
    public ColorSensor colorSensor;
    public int currentColor;
    public int colorSensorRed;

    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize dashboard
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());

        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        DcMotorEx leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        DcMotorEx rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to begin
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        sleep(5000);
        // Drive forward
        //leftMotor.setPower(0.7);
        //rightMotor.setPower(0.7);
        //sleep(400);

        while (!isStopRequested())
        {
            // Read distance
            currentDistance = distanceSensor.getDistance(DistanceUnit.INCH);
            currentColor = colorSensor.argb();
            colorSensorRed = colorSensor.red();

            telemetry.addData("Current Distance: ", currentDistance);

            // White == 1133844630
            // Black == 83952001
            // Mid-point == 608898315
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            //if (currentColor > 608898315) {
            if (currentColor > 233898315) {
                telemetry.addData("Current Color is white: ", currentColor);
            } else {
                telemetry.addData("Current Color is black: ", currentColor);
            }
            telemetry.addData("Current Red Color: ", colorSensorRed);

            telemetry.addData("Current Distance: ", currentDistance);
            telemetry.update();

            /*if (currentColor < 608898315 && colorSensorRed < 160) {
                // Drive backward
                leftMotor.setPower(-0.5);
                rightMotor.setPower(-0.5);
                sleep(750);
            }*/
            if (currentDistance >= 30)
            {
                // Drive in a circle looking for the opponent
                leftMotor.setPower(-0.8);
                rightMotor.setPower(0.8);
                sleep(50);
                leftMotor.setPower(0.0);
                rightMotor.setPower(0.0);
                sleep(250);
            }
            else
            {
                // Drive forward
                leftMotor.setPower(1.0);
                rightMotor.setPower(0.7);
            }
        }

        // Park the robot
        //drive.followTrajectorySequence(parkTrajectory);
        //idle();
    }
}