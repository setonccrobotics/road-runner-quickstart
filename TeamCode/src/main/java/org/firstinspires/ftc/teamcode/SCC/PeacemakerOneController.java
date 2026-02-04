package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name="PeacemakerOneController", group="SCC")
public class PeacemakerOneController extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        RobotVision robotVision = new RobotVision();
        //McWinnerLaunchDebug mcWinnerConveyor = new McWinnerLaunchDebug(hardwareMap);
        RobotConveyor robotConveyor = new RobotConveyor(hardwareMap);
        RobotLiftServo robotLift = new RobotLiftServo(hardwareMap);

        waitForStart();

        // Zero the robot
        robotConveyor.zero();
        robotLift.zero();
        robotVision.zero(hardwareMap);

        while (opModeIsActive()) {
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
                } else if (leftOffset < lowerOffset) {
                    // No, spin to the right
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(
                                    0.0,
                                    0.0
                            ),
                            -0.1
                    ));
                } else {
                    // No, spin to the right
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(
                                    0.0,
                                    0.0
                            ),
                            0.0
                    ));
                }
            } else {
                // Drive the robot with roadrunner via gamepad 1 input
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x
                        ),
                        -gamepad1.right_stick_x
                ));
            }
            drive.updatePoseEstimate();
            // End drive robot with roadrunner via gamepad 1 input

            // Service the robot hardware
            robotConveyor.runToo(gamepad1, robotVision);
            robotLift.run(gamepad1);

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