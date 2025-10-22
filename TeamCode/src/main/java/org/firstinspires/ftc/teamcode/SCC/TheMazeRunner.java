package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name="TheMazeRunner", group="SCC")
@Disabled
public class TheMazeRunner extends LinearOpMode {
    public DistanceSensor frontDistanceSensor;
    public DistanceSensor leftDistanceSensor;
    public DistanceSensor rightDistanceSensor;
    public double currentFrontDistance;
    public double currentLeftDistance;
    public double currentRightDistance;
    public double driveFactor = 1.0; // 2.0 == full speed ... 1.0 == half

    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize dashboard
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());

        frontDistanceSensor = hardwareMap.get(DistanceSensor.class, "frontDistanceSensor");
        leftDistanceSensor = hardwareMap.get(DistanceSensor.class, "leftDistanceSensor");
        rightDistanceSensor = hardwareMap.get(DistanceSensor.class, "rightDistanceSensor");
        DcMotorEx leftMotor = hardwareMap.get(DcMotorEx.class, "leftMotor");
        DcMotorEx rightMotor = hardwareMap.get(DcMotorEx.class, "rightMotor");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to begin
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        while (!isStopRequested())
        {
            // Read distance
            currentFrontDistance = frontDistanceSensor.getDistance(DistanceUnit.INCH);
            currentLeftDistance = leftDistanceSensor.getDistance(DistanceUnit.INCH);
            currentRightDistance = rightDistanceSensor.getDistance(DistanceUnit.INCH);
            telemetry.addData("Current Front Distance: ", currentFrontDistance);
            telemetry.addData("Current Left Distance: ", currentLeftDistance);
            telemetry.addData("Current Right Distance: ", currentRightDistance);
            telemetry.update();

            if (currentFrontDistance > 4.0) {
                // Drive forward
                if (currentLeftDistance + 1.5 < currentRightDistance) {
                    // Drive forward and to the right
                    leftMotor.setPower(0.50 * driveFactor);
                    rightMotor.setPower(0.30 * driveFactor);
                } else if (currentRightDistance + 1.5 < currentLeftDistance) {
                    // Drive forward and to the left
                    leftMotor.setPower(0.30 * driveFactor);
                    rightMotor.setPower(0.50 * driveFactor);
                } else {
                    // Drive straight forward
                    leftMotor.setPower(0.50 * driveFactor);
                    rightMotor.setPower(0.50 * driveFactor);
                }
            } else if (currentLeftDistance > currentRightDistance) {
                // Turn left
                leftMotor.setPower(-0.50);
                rightMotor.setPower(0.50);
                sleep(1000);
            } else {
                // Turn right
                leftMotor.setPower(0.50);
                rightMotor.setPower(-0.50);
                sleep(1000);
            }
        }
    }
}