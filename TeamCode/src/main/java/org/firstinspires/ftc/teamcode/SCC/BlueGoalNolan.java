package org.firstinspires.ftc.teamcode.SCC;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="BlueGoalNolan", group="SCC")
public class BlueGoalNolan extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Define the the predicted field positions (NEVER change these numbers, only the next block of code
        Pose2d startPos = new Pose2d(-62, -33, Math.toRadians(90));
        Pose2d launchPosOne = new Pose2d(-22, -22, Math.toRadians(44));
        Pose2d firstTapeMark = new Pose2d(-12, -30, Math.toRadians(270));
        Pose2d firstTapeMarkEnd = new Pose2d(-12, -54, Math.toRadians(270));
        Pose2d launchPosTwo = new Pose2d(-22, -22, Math.toRadians(44));
        Pose2d secondTapeMark = new Pose2d(12, -30, Math.toRadians(270));
        Pose2d secondTapeMarkEnd = new Pose2d(12, -54, Math.toRadians(270));

        //define strafes to get to x Pos
        Pose2d launchPos = new Pose2d(-22, -22, Math.toRadians(44));
        Pose2d launchPosThree = new Pose2d(-22, -22, Math.toRadians(40));
        Pose2d tapeMarkOneStartPos = new Pose2d(-12, -30, Math.toRadians(272));
        Vector2d tapeMarkOneEndPos = new Vector2d(-12, -56);
        Pose2d tapeMarkTwoStartPos = new Pose2d(12, -28, Math.toRadians(180));
        Vector2d tapeMarkTwoEndPos = new Vector2d(12, -64);
        Pose2d parkPos = new Pose2d(-12, -30, Math.toRadians(40));

        //sets the Hardware map
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        //sets constrants for velocity
        TranslationalVelConstraint slowVel = new TranslationalVelConstraint(8.0);
        ProfileAccelConstraint slowAccel = new ProfileAccelConstraint(-20.0, 20.0);
        TranslationalVelConstraint fullVel = new TranslationalVelConstraint(100.0);
        ProfileAccelConstraint fullAccel = new ProfileAccelConstraint(-80.0, 80.0);

        // Define the robot actions
        Action startToLaunchOne = drive.actionBuilder(startPos)
                .strafeToSplineHeading(launchPos.position, launchPos.heading, fullVel, fullAccel)
                .build();

        Action launchOneToTapeMarkOne = drive.actionBuilder(launchPosOne)
                .strafeToSplineHeading(tapeMarkOneStartPos.position, tapeMarkOneStartPos.heading, fullVel, fullAccel)
                .build();

        Action toTapeMarkOneEnd = drive.actionBuilder(firstTapeMark)
                .strafeToConstantHeading(tapeMarkOneEndPos, slowVel, slowAccel)
                .build();

        Action tapeMarkOneEndToLaunchTwo = drive.actionBuilder(firstTapeMarkEnd)
                //open gate
                .strafeToConstantHeading(new Vector2d(-12.0, -50.0), fullVel)//this backs up from previous position
                .splineToSplineHeading(new Pose2d(-4.0, -57.0, Math.toRadians(180.0)), Math.toRadians(270.0), fullVel)//this strafes to open gate
                //go to launch position
                .strafeToSplineHeading(launchPos.position, launchPos.heading, fullVel, fullAccel)
                .build();

        Action launchTwoToTapeMarkTwo = drive.actionBuilder(launchPosTwo)
                //Line two
                .strafeToSplineHeading(tapeMarkTwoStartPos.position, tapeMarkTwoStartPos.heading, fullVel, fullAccel)
                .build();


        Action toTapeMarkTwoToEnd = drive.actionBuilder(secondTapeMark)
                //go forward thrugh balls
                .strafeToConstantHeading(tapeMarkTwoEndPos, slowVel, slowAccel)
                .build();

        Action tapeMarkTwoEndToLaunchThree = drive.actionBuilder(secondTapeMarkEnd)
                //go to launch position
                .strafeToSplineHeading(launchPosThree.position, launchPosThree.heading, fullVel, fullAccel)
                .build();

        Action park = drive.actionBuilder(launchPosTwo)
                //go to launch position
                .strafeToSplineHeading(parkPos.position, parkPos.heading, fullVel, fullAccel)
                .build();

        RobotControl robotControl = new RobotControl(hardwareMap);

        // Wait for the DS start button to be touched.
        waitForStart();

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(robotControl.launchMotorOn(),
                startToLaunchOne,
                robotControl.launchBalls(),
                launchOneToTapeMarkOne,
                robotControl.conveyorOn(),
                toTapeMarkOneEnd,
                robotControl.conveyorOff(),
                tapeMarkOneEndToLaunchTwo,
                robotControl.launchBalls(),

                launchTwoToTapeMarkTwo,
                robotControl.conveyorOn(),
                toTapeMarkTwoToEnd,
                robotControl.conveyorOff(),
                tapeMarkTwoEndToLaunchThree,
                robotControl.conveyorOff(),
                robotControl.launchBalls(),
                park,
                robotControl.launchMotorOff()
        ));
    }
}