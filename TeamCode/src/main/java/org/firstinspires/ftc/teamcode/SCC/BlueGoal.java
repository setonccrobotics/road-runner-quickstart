package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
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
        Pose2d launchPos = new Pose2d(-22, -22, Math.toRadians(44));
        Pose2d parkPos = new Pose2d(-12.0, -30.0, Math.toRadians(40.0));
        Pose2d tapeMarkOneStartPos = new Pose2d(-12, -30, Math.toRadians(270));
        Pose2d tapeMarkOneEndPos = new Pose2d(-12, -56, Math.toRadians(270));
        Pose2d tapeMarkTwoStartPos = new Pose2d(12, -28, Math.toRadians(260));
        Pose2d tapeMarkTwoEndPos = new Pose2d(12.0, -64.0, Math.toRadians(260));
        Pose2d openGateStartPos = new Pose2d(-12.0, -50.0, Math.toRadians(180));
        Pose2d openGateEndPos = new Pose2d(-4.0, -57.0, Math.toRadians(180));



        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        TranslationalVelConstraint slowVel = new TranslationalVelConstraint(8.0);
        ProfileAccelConstraint slowAccel = new ProfileAccelConstraint(-20.0, 20.0);
        TranslationalVelConstraint fullVel = new TranslationalVelConstraint(100.0);
        ProfileAccelConstraint fullAccel = new ProfileAccelConstraint(-80.0, 80.0);

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .strafeToSplineHeading(launchPos.position, launchPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosOneToTapeMarkOne = drive.actionBuilder(launchPos)
                .strafeToSplineHeading(tapeMarkOneStartPos.position, tapeMarkOneStartPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromTapeMarkOneStartToEnd = drive.actionBuilder(tapeMarkOneStartPos)
                .strafeToConstantHeading(tapeMarkOneEndPos.position, slowVel, slowAccel)
                .build();

        Action driveFromTapeMarkOneEndToLaunchPosOne = drive.actionBuilder(tapeMarkOneEndPos)
                //open gate
                .strafeToConstantHeading(openGateStartPos.position, fullVel)
                .splineToSplineHeading(openGateEndPos, tapeMarkOneEndPos.heading, fullVel)
                //go to launch position
                .strafeToSplineHeading(launchPos.position, launchPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosToTapeMarkTwo = drive.actionBuilder(launchPos)
                //Line two
                .strafeToSplineHeading(tapeMarkTwoStartPos.position, tapeMarkTwoStartPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromTapeMarkTwoToEnd = drive.actionBuilder(tapeMarkTwoStartPos)
                //go forward through balls
                .strafeToConstantHeading(tapeMarkTwoEndPos.position, slowVel, slowAccel)
                .build();

        Action driveFromTapeMarkEndToLaunchPosThree = drive.actionBuilder(tapeMarkTwoEndPos)
                //go to launch position
                .strafeToSplineHeading(launchPos.position, parkPos.heading, fullVel, fullAccel)
                //.strafeToSplineHeading(launchPos.position, launchPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosToPark = drive.actionBuilder(launchPos)
                //go to launch position
                .strafeToSplineHeading(parkPos.position, parkPos.heading, fullVel, fullAccel)
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
                driveFromTapeMarkOneStartToEnd,
                robotControl.conveyorOff(),
                driveFromTapeMarkOneEndToLaunchPosOne,
                robotControl.launchBalls(),
                driveFromLaunchPosToTapeMarkTwo,
                robotControl.conveyorOn(),
                driveFromTapeMarkTwoToEnd,
                robotControl.conveyorOff(),
                driveFromTapeMarkEndToLaunchPosThree,
                robotControl.conveyorOff(),
                robotControl.launchBalls(),
                driveFromLaunchPosToPark,
                robotControl.launchMotorOff()
        ));
    }
}