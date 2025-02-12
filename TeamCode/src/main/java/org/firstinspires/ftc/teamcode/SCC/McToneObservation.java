package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name="McToneObservation", group="SCC")
@Disabled
public class McToneObservation extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        OverheadLift overheadLift = new OverheadLift(hardwareMap);
        LiftAssembly liftAssembly = new LiftAssembly(hardwareMap);
        ClawAssembly clawAssembly = new ClawAssembly(hardwareMap);
        double driveFactor = 0.5;

        waitForStart();

        // Zero the robot
        liftAssembly.zero();
        overheadLift.zero();
        clawAssembly.zero();

        while (opModeIsActive()) {
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
            overheadLift.run(gamepad1);
            liftAssembly.run(gamepad1, gamepad2);
            clawAssembly.run(gamepad2, liftAssembly.slideMotorCurrentPosition());

            // Update the screen output with interesting data
            clawAssembly.addTelemetry(telemetry);
            liftAssembly.addTelemetry(telemetry);
            overheadLift.addTelemetry(telemetry);
            //telemetry.addData("x", drive.pose.position.x);
            //telemetry.addData("y", drive.pose.position.y);
            //telemetry.addData("heading", drive.pose.heading);
            //telemetry.addData("gamepad2.left_trigger", "%.2f", gamepad2.left_trigger);
            //telemetry.addData("gamepad2.right_trigger", "%.2f", gamepad2.right_trigger);
            telemetry.update();
        }
    }
}