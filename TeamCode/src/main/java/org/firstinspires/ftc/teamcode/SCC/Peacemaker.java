package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name="Peacemaker", group="SCC")
public class Peacemaker extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        RobotVision robotVision = new RobotVision();
        //McWinnerLaunchDebug mcWinnerConveyor = new McWinnerLaunchDebug(hardwareMap);
        RobotConveyor robotConveyor = new RobotConveyor(hardwareMap);
        RobotLiftServo robotLift = new RobotLiftServo(hardwareMap);
        double driveFactor = 0.5;
        LedStrip ledStrip = new LedStrip(hardwareMap);
        Pose2d previousPosition = new Pose2d(0, 0, Math.toRadians(0.0));

        // Turn on the rainbow animation
        ledStrip.setLedColor(0.2);

        waitForStart();

        // Zero the robot
        robotConveyor.zero();
        robotLift.zero();
        robotVision.zero(hardwareMap);
        ledStrip.setLedColor(0.28);

        while (opModeIsActive()) {
            if (gamepad1.right_trigger > 0.2) {
                driveFactor = 0.25;
            } else {
                driveFactor = 1.0;
            }
            if (gamepad1.left_trigger > 0.2) {
                driveFactor *= -1.0;
            }
            if (gamepad1.a) {
                // Enter auto aiming mode
                double leftOffset = robotVision.getLeftOffset();
                //double distance = robotVision.getDistance();
                int id = robotVision.getTagId();
                double upperOffset = 1.0;
                double lowerOffset = -1.0;


                // Should we turn to the left
                if (leftOffset > upperOffset) {
                    // Yes, spin to the left
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(
                                    0.0,
                                    0.0
                            ),
                            0.1
                    ));
                    ledStrip.setLedColor(0.06);
                } else if (leftOffset < lowerOffset) {
                    // No, spin to the right
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(
                                    0.0,
                                    0.0
                            ),
                            -0.1
                    ));
                    ledStrip.setLedColor(0.06);
                } else {
                    // target acquired
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(
                                    0.0,
                                    0.0
                            ),
                            0.0
                    ));
                    if (robotConveyor.getLaunchVelocity() + 22.0 > robotConveyor.getTargetLaunchVelocity()) {
                        ledStrip.setLedColor(0.45); // We are up to speed!
                    } else {
                        ledStrip.setLedColor(0.11); // We are on target but not up to speed yet!
                    }
                }
            } else {
                // Has the position of the robot changed?
                if (gamepad1.left_stick_y != 0.0 || gamepad1.left_stick_x != 0.0 || gamepad1.right_stick_x != 0.0){
                    // Yes, set the LED red
                    ledStrip.setLedColor(0.28);
                }
                /*telemetry.addData("gamepad1.left_stick_y", "%.2f", gamepad1.left_stick_y);
                telemetry.addData("gamepad1.left_stick_x", "%.2f", gamepad1.left_stick_x);
                telemetry.addData("gamepad1.right_stick_x", "%.2f", gamepad1.right_stick_x);
                telemetry.addData("robotConveyor.getLaunchVelocity()", "%.2f", robotConveyor.getLaunchVelocity());
                telemetry.addData("robotConveyor.getTargetLaunchVelocity()", "%.2f", robotConveyor.getTargetLaunchVelocity());
                telemetry.update();*/

                // Drive the robot with roadrunner via gamepad 1 input
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y * driveFactor,
                                -gamepad1.left_stick_x * driveFactor
                        ),
                        -gamepad1.right_stick_x * driveFactor
                ));
            }
            drive.updatePoseEstimate();
            // End drive robot with roadrunner via gamepad 1 input

            // Service the robot hardware
            robotConveyor.run(gamepad2, robotVision);
            robotLift.run(gamepad1, ledStrip);

            // Update the screen output with interesting data
            //telemetry.addData("heading", drive.pose.heading);
            //telemetry.addData("gamepad2.left_trigger", "%.2f", gamepad2.left_trigger);
            //telemetry.addData("gamepad2.right_trigger", "%.2f", gamepad2.right_trigger);
            ///robotConveyor.addTelemetry(telemetry);
            //robotLift.addTelemetry(telemetry);
            //robotVision.run(telemetry);
            //telemetry.update();
        }
    }
}