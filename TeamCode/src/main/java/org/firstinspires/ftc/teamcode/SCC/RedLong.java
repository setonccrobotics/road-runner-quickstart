package org.firstinspires.ftc.teamcode.SCC;

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

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="RedLong", group="SCC")
public class RedLong extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // Define the field positions
        Pose2d startPos = new Pose2d(62, 9, Math.toRadians(0));
        Pose2d launchPos = new Pose2d(-6, 11, Math.toRadians(-45));
        Pose2d parkPos = new Pose2d(3, 16, Math.toRadians(-45));
        Pose2d tapeMarkStart = new Pose2d(35, 28, Math.toRadians(-270));
        Pose2d tapeMarkEnd = new Pose2d(35, 55, Math.toRadians(-270));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);
        RobotControl robotControl = new RobotControl(hardwareMap);

        // Sets constraints for velocity
        TranslationalVelConstraint slowVel = new TranslationalVelConstraint(8.0);
        ProfileAccelConstraint slowAccel = new ProfileAccelConstraint(-8.0, 8.0);
        TranslationalVelConstraint fullVel = new TranslationalVelConstraint(100.0);
        ProfileAccelConstraint fullAccel = new ProfileAccelConstraint(-80.0, 80.0);

        // Define the robot actions
        Action driveFromStartToLaunchPos = drive.actionBuilder(startPos)
                .lineToX(launchPos.position.x + 10, fullVel, fullAccel)
                .splineToLinearHeading(launchPos, launchPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosToTapeMarkStart = drive.actionBuilder(launchPos)
                .turnTo(startPos.heading)
                .lineToX(tapeMarkStart.position.x - 12, fullVel, fullAccel)
                .splineToLinearHeading(tapeMarkStart, tapeMarkStart.heading, fullVel, fullAccel)
                .build();

        Action driveFromTapeMarkStartToTapeMarkEnd = drive.actionBuilder(tapeMarkStart)
                .strafeToConstantHeading(tapeMarkEnd.position, slowVel, slowAccel)
                .build();

        Action driveFromTapeMarkEndToLaunchPos = drive.actionBuilder(tapeMarkEnd)
                .lineToY(launchPos.position.y + 10, fullVel, fullAccel)
                .splineToLinearHeading(launchPos, launchPos.heading, fullVel, fullAccel)
                .build();

        Action driveFromLaunchPosToPark = drive.actionBuilder(launchPos)
                .splineToLinearHeading(parkPos, parkPos.heading, fullVel, fullAccel)
                .build();

        //Action autoLaunchBall = RobotConveyor.AutoLaunchBall;

        // Wait for the DS start button to be touched
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(robotControl.launchMotorOnLong(),
                driveFromStartToLaunchPos,
                robotControl.launchBalls(),
                driveFromLaunchPosToTapeMarkStart,
                robotControl.conveyorOn(),
                driveFromTapeMarkStartToTapeMarkEnd,
                robotControl.conveyorOff(),
                driveFromTapeMarkEndToLaunchPos,
                robotControl.launchBalls(),
                driveFromLaunchPosToPark,
                robotControl.launchMotorOff()
        ));
    }
}