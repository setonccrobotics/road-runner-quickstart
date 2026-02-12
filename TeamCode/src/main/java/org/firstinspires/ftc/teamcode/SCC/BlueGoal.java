package org.firstinspires.ftc.teamcode.SCC;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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
        Pose2d startPos = new Pose2d(-62, -33, Math.toRadians(90));
        Pose2d launchPosOne = new Pose2d(-35, -16.1, Math.toRadians(54));
        Pose2d tapeMarkOnePos = new Pose2d(-16, -28, Math.toRadians(-88));
        Pose2d tapeMark1APos = new Pose2d(-16, -33, Math.toRadians(-88));
        Pose2d tapeMark1BPos = new Pose2d(-16, -38, Math.toRadians(-88));
        Pose2d tapeMark1CPos = new Pose2d(-16, -43, Math.toRadians(-88));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        TranslationalVelConstraint slowVel = new TranslationalVelConstraint(8.0);
        ProfileAccelConstraint slowAccel = new ProfileAccelConstraint(-8.0, 8.0);
        TranslationalVelConstraint fullVel = new TranslationalVelConstraint(100.0);
        ProfileAccelConstraint fullAccel = new ProfileAccelConstraint(-100.0, 100.0);

        Actions.runBlocking(new SequentialAction(drive.actionBuilder(new Pose2d(-62.0, -33.0, Math.toRadians(90.00)))
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                .strafeToSplineHeading(new Vector2d(-12.0, -30.0), Math.toRadians(270.0), fullVel, fullAccel)
                .strafeToConstantHeading(new Vector2d(-12.0, -54.0), fullVel, fullAccel)
                .strafeToConstantHeading(new Vector2d(-12.0, -50.0), fullVel, fullAccel)
                .splineToSplineHeading(new Pose2d(-4.0, -55.0, Math.toRadians(180.0)), Math.toRadians(270.0), fullVel, fullAccel)
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                .strafeToSplineHeading(new Vector2d(12.0, -30.0), Math.toRadians(270.0), fullVel, fullAccel)
                .strafeToConstantHeading(new Vector2d(12.0, -50.0), fullVel, fullAccel)
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                .build()));
        /*
        Actions.runBlocking(new SequentialAction(drive.actionBuilder(new Pose2d(-56, -44, Math.toRadians(55.00)))
                .lineToXConstantHeading(-34)
                .splineToSplineHeading(new Pose2d(-12, -30, Math.toRadians(270.00)), Math.toRadians(-37.37))
                .strafeToSplineHeading(new Vector2d(-12, -36), Math.toRadians(270.00))
                .strafeToSplineHeading(new Vector2d(-12, -42), Math.toRadians(270.00))
                .strafeToSplineHeading(new Vector2d(-12, -48), Math.toRadians(270.00))
                .splineToSplineHeading(new Pose2d(-36, -20, Math.toRadians(55.00)), Math.toRadians(114.00))
                .build()));
        */
        /*
        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .strafeTo(launchPosOne.position)
                .build();

        Action driveFromLaunchPosOneToTapeMarkOne = drive.actionBuilder(launchPosOne)
                .splineTo(tapeMarkOnePos.position, tapeMarkOnePos.heading)
                .build();

        TranslationalVelConstraint slowVel = new TranslationalVelConstraint(8.0);
        ProfileAccelConstraint slowAccel = new ProfileAccelConstraint(-8.0, 8.0);

        Action driveFromTapeMarkOneTo1A = drive.actionBuilder(tapeMarkOnePos)
                .splineTo(tapeMark1APos.position, tapeMark1APos.heading, slowVel, slowAccel)
                .build();

        Action driveFromTapeMark1ATo1B = drive.actionBuilder(tapeMark1APos)
                .splineTo(tapeMark1BPos.position, tapeMark1BPos.heading, slowVel, slowAccel)
                .build();

        Action driveFromTapeMark1BTo1C = drive.actionBuilder(tapeMark1BPos)
                .splineTo(tapeMark1CPos.position, tapeMark1CPos.heading, slowVel, slowAccel)
                .build();

        Action driveFromTapeMark1CToLaunchPosOne = drive.actionBuilder(tapeMark1CPos)
                .splineTo(launchPosOne.position, launchPosOne.heading)
                .build();

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        RobotControl robotControl = new RobotControl(hardwareMap);

        if (isStopRequested()) return;

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(robotControl.launchMotorOn(),
                driveFromStartToLaunchPosOne,
                robotControl.launchBalls(),
                driveFromLaunchPosOneToTapeMarkOne,
                robotControl.conveyorOn(),
                driveFromTapeMarkOneTo1A,
                robotControl.sleepHalfSecond(),
                robotControl.conveyorOn(),
                driveFromTapeMark1ATo1B,
                robotControl.sleepHalfSecond(),
                robotControl.conveyorOn(),
                driveFromTapeMark1BTo1C,
                robotControl.sleepHalfSecond(),
                driveFromTapeMark1CToLaunchPosOne,
                robotControl.conveyorOff(),
                robotControl.launchBalls(),
                robotControl.launchMotorOff()
        ));
         */
    }
}