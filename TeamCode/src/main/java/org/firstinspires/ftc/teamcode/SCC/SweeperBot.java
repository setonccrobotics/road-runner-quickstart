package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;

@TeleOp(name="SweeperBot", group="SCC")
public class SweeperBot extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        TankDrive drive = new TankDrive(hardwareMap, new Pose2d(0, 0, 0));

        double driveFactor = 0.5;

        waitForStart();

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

            // Update the screen output with interesting data
            //telemetry.addData("heading", drive.pose.heading);
            telemetry.update();
        }
    }
}