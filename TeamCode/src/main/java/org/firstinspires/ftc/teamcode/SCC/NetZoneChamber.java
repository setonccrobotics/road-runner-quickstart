package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="NetZoneChamber", group="SCC")
@Disabled
public class NetZoneChamber extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        LiftAssembly liftAssembly = new LiftAssembly(hardwareMap);
        ClawAssembly clawAssembly = new ClawAssembly(hardwareMap);

        // Define the field positions
        Pose2d startPos = new Pose2d(0, 0, Math.toRadians(90.0));
        Pose2d submersiblePos = new Pose2d(0, 26, Math.toRadians(90.0));
        Pose2d startOffWallPos = new Pose2d(0, 5, Math.toRadians(90.0));
        Pose2d tapeMark1 = new Pose2d(-40.25,24.5, Math.toRadians(90.0));
        Pose2d netZonePos = new Pose2d(-41, 12, Math.toRadians(45.0));
        /*Pose2d parkPos = new Pose2d(36, 4, Math.toRadians(90.0));
        Pose2d postnetZonePos =  new Pose2d(-43.5, 64, Math.toRadians(0.0));
        Pose2d assentZonePos =  new Pose2d(-43.5, 11, Math.toRadians(0.0));
        Pose2d assentParkPos =  new Pose2d(-45.5, 11, Math.toRadians(180.0));*/

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToStartOffWallPos = drive.actionBuilder(startPos)
                .lineToY(startOffWallPos.position.y)
                .build();

        Action driveFromStartOffWallToSubmersiblePos = drive.actionBuilder(startOffWallPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromSubmersibleToTapeMark1Pos = drive.actionBuilder(submersiblePos)
                .strafeTo(new Vector2d(tapeMark1.position.x, tapeMark1.position.y))
                .build();

        Action driveFromTapeMark1ToNetZonePos = drive.actionBuilder(tapeMark1)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();
        /*
        Action driveFromStartOffWallToParkPos = drive.actionBuilder(startOffWallPos)
                .strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                .build();
        Action driveFromPostNetZoneToAssentZonePos = drive.actionBuilder(postnetZonePos)
                .strafeTo(new Vector2d(assentZonePos.position.x, assentZonePos.position.y))
                .build();
        Action driveFromAssentZoneToAssentParkPos = drive.actionBuilder(assentZonePos)
                //.splineTo(new Vector2d(assentParkPos.position.x, assentParkPos.position.y), Math.toRadians(assentParkPos.heading.real))
                .splineToLinearHeading(assentParkPos, assentParkPos.heading)
                .build();
        */

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // First thing first!! Move the lift out of the way
        clawAssembly.clawClose();
        liftAssembly.zero();
        clawAssembly.zero();

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawAssembly.setWristPos(0.66);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromStartToStartOffWallPos));

        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;

        liftAssembly.zeroLiftOnly();

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        liftAssembly.liftToEncoderPos(-1500);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        // Drive up to the spike mark
        Actions.runBlocking(new SequentialAction(driveFromStartOffWallToSubmersiblePos));

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        liftAssembly.slideToEncoderPos(807);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        liftAssembly.liftToEncoderPos(-1000);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawAssembly.clawOpen();

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        liftAssembly.slideToEncoderPos(137);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSubmersibleToTapeMark1Pos));

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        //-300 lift, slide 2800, lift -130
        liftAssembly.liftToEncoderPos(-400);
        sleep(250);
        liftAssembly.slideToEncoderPos(752);
        sleep(250);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.9);
        sleep(1000);
        liftAssembly.liftToEncoderPos(-500);
        sleep(400);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        sleep(700);
        liftAssembly.liftToEncoderPos(-1100);
        sleep(1000);
        if (isStopRequested()) return;
        liftAssembly.extendStabilityServo();

        Actions.runBlocking(new SequentialAction(driveFromTapeMark1ToNetZonePos));

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        liftAssembly.liftToTop();

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        liftAssembly.slideToEncoderPos(2701);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        clawAssembly.setWristPos(0.60);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawAssembly.clawOpen();

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawAssembly.setWristPos(0.80);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        liftAssembly.slideToEncoderPos(137);

        if (isStopRequested()) return;
        sleep(2400);
        if (isStopRequested()) return;

        liftAssembly.liftToEncoderPos(-1100);

        sleep(1000);

        /*
        Actions.runBlocking(new SequentialAction(driveFromNetZoneToPostNetZonePos));
        if (isStopRequested()) return;

        sleep(1000);
        Actions.runBlocking(new SequentialAction(driveFromPostNetZoneToAssentZonePos));
        if (isStopRequested()) return;

        sleep(1000);
        Actions.runBlocking(new SequentialAction(driveFromAssentZoneToAssentParkPos));
        if (isStopRequested()) return;

        simpleClaw.setWristPos(0.35);
        sleep(1000);
        liftAssembly.liftToEncoderPos(-1600);
        sleep(1000);
        liftAssembly.slideToEncoderPos(2257);
        sleep(3000);
         */
    }
}
