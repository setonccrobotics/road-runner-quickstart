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

@Autonomous(name="NetZoneBucket", group="SCC")
@Disabled
public class NetZoneBucket extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        LiftAssembly liftAssembly = new LiftAssembly(hardwareMap);
        ClawAssembly clawAssembly = new ClawAssembly(hardwareMap);

        // Define the field positions
        Pose2d startPos = new Pose2d(-24, 0, Math.toRadians(90.0));
        Pose2d startOffWallPos = new Pose2d(-24, 4, Math.toRadians(90.0));
        Pose2d netZonePos = new Pose2d(-41, 11, Math.toRadians(45.0));
        Pose2d tapeMark1 = new Pose2d(-39,29, Math.toRadians(90.0));
        Pose2d tapeMark2 = new Pose2d(-50,29, Math.toRadians(90.0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToStartOffWallPos = drive.actionBuilder(startPos)
                .lineToY(startOffWallPos.position.y)
                .build();

        Action driveFromStartOffWallToNetPos = drive.actionBuilder(startOffWallPos)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        Action driveFromTapeMark1ToNetZonePos = drive.actionBuilder(tapeMark1)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        Action driveFromTapeMark2ToNetZonePos = drive.actionBuilder(tapeMark2)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        Action driveFromNetToTapeMark1Pos = drive.actionBuilder(netZonePos)
                .turnTo(tapeMark1.heading)
                .splineToLinearHeading(tapeMark1, tapeMark1.heading)
                .build();

        Action driveFromNetToTapeMark2Pos = drive.actionBuilder(netZonePos)
                .turnTo(tapeMark2.heading)
                .splineToLinearHeading(tapeMark2, tapeMark2.heading)
                .build();

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

        clawAssembly.setWristPos(0.66);

        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromStartToStartOffWallPos));

        if (isStopRequested()) return;

        liftAssembly.zeroLiftOnly();

        // Drive to the net zone
        Actions.runBlocking(new SequentialAction(driveFromStartOffWallToNetPos));

        // Deliver sample in bucket
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.8);
        liftAssembly.slideToEncoderPos(2900);
        if (isStopRequested()) return;
        sleep(1400);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.60);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.80);
        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPos(137);
        if (isStopRequested()) return;
        sleep(1400);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-1100);
        sleep(100);

        Actions.runBlocking(new SequentialAction(driveFromNetToTapeMark1Pos));

        if (isStopRequested()) return;

        // pick up sample off floor
        liftAssembly.liftToEncoderPos(-400);
        sleep(250);
        //liftAssembly.slideToEncoderPos(740);
        //sleep(250);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.9);
        sleep(300);
        liftAssembly.liftToEncoderPos(-490);
        sleep(400);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        sleep(300);
        liftAssembly.liftToEncoderPos(-1100);
        sleep(200);
        if (isStopRequested()) return;
        liftAssembly.extendStabilityServo();

        Actions.runBlocking(new SequentialAction(driveFromTapeMark1ToNetZonePos));

        if (isStopRequested()) return;

        // Deliver sample in bucket
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.8);
        liftAssembly.slideToEncoderPos(2900);
        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.60);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.80);
        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPos(137);
        if (isStopRequested()) return;
        sleep(1400);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-1100);
        sleep(100);

        Actions.runBlocking(new SequentialAction(driveFromNetToTapeMark2Pos));

        if (isStopRequested()) return;

        // pick up sample off floor
        liftAssembly.liftToEncoderPos(-400);
        sleep(250);
        //liftAssembly.slideToEncoderPos(740);
        //sleep(250);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.9);
        sleep(400);
        liftAssembly.liftToEncoderPos(-490);
        sleep(400);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        sleep(300);
        liftAssembly.liftToEncoderPos(-1100);
        sleep(300);
        if (isStopRequested()) return;
        liftAssembly.extendStabilityServo();

        Actions.runBlocking(new SequentialAction(driveFromTapeMark2ToNetZonePos));

        if (isStopRequested()) return;

        // Deliver sample in bucket
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.8);
        liftAssembly.slideToEncoderPos(2900);
        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.60);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        clawAssembly.setWristPos(0.80);
        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPos(137);
        if (isStopRequested()) return;
        sleep(2400);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-1100);
        sleep(0);
    }
}
