package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name="WinnerMcWinnerFace", group="SCC")
public class WinnerMcWinnerFace extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        RobotVision robotVision = new RobotVision();
        //McWinnerConveyor mcWinnerConveyor = new McWinnerConveyor(hardwareMap);
        McWinnerConveyor mcWinnerConveyor = new McWinnerConveyor(hardwareMap);
        double driveFactor = 0.5;

        waitForStart();

        // Zero the robot
        mcWinnerConveyor.zero();
        robotVision.zero(hardwareMap);

        while (opModeIsActive()) {
            if (gamepad1.right_trigger > 0.2) {
                driveFactor = 0.25;
            } else {
                driveFactor = 1.0;
            }
            if (gamepad1.left_trigger > 0.2) {
                driveFactor *= -1.0;
            }
            // Drive the robot with roadrunner via gamepad 1 input
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * driveFactor,
                            -gamepad1.left_stick_x * driveFactor
                    ),
                    -gamepad1.right_stick_x * driveFactor
            ));
            drive.updatePoseEstimate();
            // End drive robot with roadrunner via gamepad 1 input

            // Service the robot hardware
            mcWinnerConveyor.run(gamepad2, robotVision);

            // Update the screen output with interesting data
            //telemetry.addData("heading", drive.pose.heading);
            //telemetry.addData("gamepad2.left_trigger", "%.2f", gamepad2.left_trigger);
            //telemetry.addData("gamepad2.right_trigger", "%.2f", gamepad2.right_trigger);
            mcWinnerConveyor.addTelemetry(telemetry);
            robotVision.run(telemetry);
            telemetry.update();
        }
    }
}