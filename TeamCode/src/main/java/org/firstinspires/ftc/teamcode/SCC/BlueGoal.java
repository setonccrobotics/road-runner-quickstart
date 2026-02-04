package org.firstinspires.ftc.teamcode.SCC;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="BlueGoal", group="SCC")
public class BlueGoal extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Define the field positions
        Pose2d startPos = new Pose2d(-58, -43, Math.toRadians(54));
        Pose2d launchPosOne = new Pose2d(-35, -16.1, Math.toRadians(54));
        Pose2d tapeMarkOnePos = new Pose2d(-16, -33, Math.toRadians(-90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .strafeTo(launchPosOne.position)
                .build();

        Action driveFromLaunchPosOneToPark = drive.actionBuilder(launchPosOne)
                .splineTo(tapeMarkOnePos.position, tapeMarkOnePos.heading)
                .build();

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        RobotControl robotControl = new RobotControl(hardwareMap);

        if (isStopRequested()) return;

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(driveFromStartToLaunchPosOne,
                robotControl.launchBalls(),
                driveFromLaunchPosOneToPark));

    }

    public boolean launchBall(RobotConveyor conveyor) {
        // Expects the launch motor to be controlled externally
        conveyor.launchBall();
        if (isStopRequested()) return false;
        sleep(2000);
        conveyor.launchGateOpen();
        if (isStopRequested()) return false;
        sleep(500);
        return true;
    }
}