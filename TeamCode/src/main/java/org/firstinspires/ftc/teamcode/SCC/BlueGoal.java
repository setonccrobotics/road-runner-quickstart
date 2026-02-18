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
        Pose2d launchPosOne = new Pose2d(-22, -22, Math.toRadians(44));
        Pose2d tapeMarkOnePos = new Pose2d(-12, -30, Math.toRadians(270));
        Pose2d tapeMarkOneEndPos = new Pose2d(-12, -54, Math.toRadians(270));
        Pose2d launchPosTwo = new Pose2d(-22, -22, Math.toRadians(44));
        Pose2d tapeMarkTwoPos = new Pose2d(12, -30, Math.toRadians(270));
        Pose2d tapeMarkTwoEndPos = new Pose2d(12, -54, Math.toRadians(270));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        TranslationalVelConstraint slowVel = new TranslationalVelConstraint(8.0);
        ProfileAccelConstraint slowAccel = new ProfileAccelConstraint(-20.0, 20.0);
        TranslationalVelConstraint fullVel = new TranslationalVelConstraint(100.0);
        ProfileAccelConstraint fullAccel = new ProfileAccelConstraint(-80.0, 80.0);
/*
        Actions.runBlocking(new SequentialAction(drive.actionBuilder(startPos)
                //Go from start position to Launch position
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                //Go to before the balls
                .strafeToSplineHeading(new Vector2d(-12.0, -30.0), Math.toRadians(270.0), fullVel, fullAccel)
                //Go thrugh the balls
                .strafeToConstantHeading(new Vector2d(-12.0, -54.0), fullVel, fullAccel)
                //Go back and open gate
                .strafeToConstantHeading(new Vector2d(-12.0, -50.0), fullVel, fullAccel)
                .splineToSplineHeading(new Pose2d(-4.0, -55.0, Math.toRadians(180.0)), Math.toRadians(270.0), fullVel, fullAccel)
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                //go to other line of balls
                .strafeToSplineHeading(new Vector2d(12.0, -30.0), Math.toRadians(270.0), fullVel, fullAccel)
                //go forward thrugh balls
                .strafeToConstantHeading(new Vector2d(12.0, -50.0), fullVel, fullAccel)
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                .build()));
        */


        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosOneToTapeMarkOne = drive.actionBuilder(launchPosOne)
                .strafeToSplineHeading(new Vector2d(-12.0, -30.0), Math.toRadians(270.0), fullVel, fullAccel)
                .build();

        Action driveFromTapeMarkOneToEnd = drive.actionBuilder(tapeMarkOnePos)
                .strafeToConstantHeading(new Vector2d(-12.0, -56.0), slowVel, slowAccel)
                .build();

        Action driveFromTapeMarkEndToLaunchPosOne = drive.actionBuilder(tapeMarkOneEndPos)
                //open gate
                .strafeToConstantHeading(new Vector2d(-12.0, -50.0), fullVel)
                .splineToSplineHeading(new Pose2d(-4.0, -57.0, Math.toRadians(180.0)), Math.toRadians(270.0), fullVel)
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0), fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosTwoToTapeMarkTwo = drive.actionBuilder(launchPosTwo)
                //Line two
                .strafeToSplineHeading(new Vector2d(12.0, -28.0), Math.toRadians(180.0), fullVel, fullAccel)
                .build();


        Action driveFromTapeMarkTwoToEnd = drive.actionBuilder(tapeMarkTwoPos)
                //go forward thrugh balls
                .strafeToConstantHeading(new Vector2d(12.0, -64.0), slowVel, slowAccel)
                .build();

        Action driveFromTapeMarkEndToLaunchPosThree = drive.actionBuilder(tapeMarkTwoEndPos)
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(40.0), fullVel, fullAccel)
                .build();

        Action park = drive.actionBuilder(launchPosTwo)
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-12.0, -30.0), Math.toRadians(40.0), fullVel, fullAccel)
                .build();

        RobotControl robotControl = new RobotControl(hardwareMap);

        // Wait for the DS start button to be touched.
        waitForStart();

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(robotControl.launchMotorOn(),
                driveFromStartToLaunchPosOne,
                robotControl.launchBalls(),
                driveFromLaunchPosOneToTapeMarkOne,
                robotControl.conveyorOn(),
                driveFromTapeMarkOneToEnd,
                robotControl.conveyorOff(),
                driveFromTapeMarkEndToLaunchPosOne,
                robotControl.launchBalls(),

                driveFromLaunchPosTwoToTapeMarkTwo,
                robotControl.conveyorOn(),
                driveFromTapeMarkTwoToEnd,
                robotControl.conveyorOff(),
                driveFromTapeMarkEndToLaunchPosThree,
                robotControl.conveyorOff(),
                robotControl.launchBalls(),
                park,
                robotControl.launchMotorOff()
        ));
    }
}