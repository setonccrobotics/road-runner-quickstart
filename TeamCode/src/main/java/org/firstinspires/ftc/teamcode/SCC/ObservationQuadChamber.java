package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="ObservationQuadChamber", group="SCC")
public class ObservationQuadChamber extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        LiftAssembly liftAssembly = new LiftAssembly(hardwareMap);
        ClawAssembly clawAssembly = new ClawAssembly(hardwareMap);

        // Define the field positions
        Pose2d startPos = new Pose2d(9, -61, Math.toRadians(270.0));
        Pose2d submersiblePos = new Pose2d(9, -43.25, Math.toRadians(270.0));
        Pose2d submersibleTwoPos = new Pose2d(6, -42, Math.toRadians(270.0));
        Pose2d submersibleThreePos = new Pose2d(3, -42, Math.toRadians(270.0));
        Pose2d submersibleFourPos = new Pose2d(0, -42, Math.toRadians(270.0));
        Pose2d specimenPos = new Pose2d(36, -55.25, Math.toRadians(270.0));
        Pose2d sampleOnePos = new Pose2d(26, -44, Math.toRadians(60.0));
        Pose2d sampleTwoPos = new Pose2d(38, -44, Math.toRadians(60.0));
        Pose2d observationSampleOnePos = new Pose2d(25, -44, Math.toRadians(-43.0));
        Pose2d observationSampleTwoPos = new Pose2d(38.5, -44, Math.toRadians(-43.0));
        Pose2d parkPos = new Pose2d(28, -53, Math.toRadians(-50.0));
        //Pose2d parkPos = new Pose2d(45, -55, Math.toRadians(-50.0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToSubmersiblePos = drive.actionBuilder(startPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromSubmersibleToSampleOnePos = drive.actionBuilder(submersiblePos)
                .splineTo(new Vector2d(sampleOnePos.position.x, sampleOnePos.position.y), sampleOnePos.heading)
                .build();

        Action deliverTwoSamplesToObservationZonePos = drive.actionBuilder(sampleOnePos)
                .splineToLinearHeading(observationSampleOnePos, observationSampleOnePos.heading)
                .splineToLinearHeading(sampleOnePos, sampleOnePos.heading)
                .splineToLinearHeading(sampleTwoPos, sampleTwoPos.heading)
                .splineToLinearHeading(observationSampleTwoPos, observationSampleTwoPos.heading)
                .build();

        Action driveFromObservationSampleTwoToSpecimenPos = drive.actionBuilder(observationSampleTwoPos)
                .turnTo(specimenPos.heading)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSpecimenToSubmersibleTwoPos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleTwoPos.position.x, submersibleTwoPos.position.y))
                .build();

        Action driveFromSubmersibleTwoToSpecimenPos = drive.actionBuilder(submersibleTwoPos)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSpecimenToSubmersibleThreePos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleThreePos.position.x, submersibleThreePos.position.y))
                .build();

        Action driveFromSubmersibleThreeToSpecimenPos = drive.actionBuilder(submersibleThreePos)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSpecimenToSubmersibleFourPos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleFourPos.position.x, submersibleFourPos.position.y))
                .build();

        Action driveFromSubmersibleFourToParkPos = drive.actionBuilder(submersibleFourPos)
                //.strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                .splineTo(new Vector2d(parkPos.position.x, parkPos.position.y), parkPos.heading)
                .build();
        /*Action driveFromSubmersibleFourToParkPos = drive.actionBuilder(submersibleFourPos)
                //.strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                .turnTo(parkPos.heading)
                .strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                //.splineTo(new Vector2d(parkPos.position.x, parkPos.position.y), parkPos.heading)
                .build();*/

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // First thing first!! Move the lift out of the way
        clawAssembly.clawClose();

        if (isStopRequested()) return;
        Actions.runBlocking(new SequentialAction(driveFromStartToSubmersiblePos));
        if (isStopRequested()) return;

        // Zero the robot
        clawAssembly.zero();
        liftAssembly.zero();
        liftAssembly.extendStabilityServo();

        // Place the preloaded specimen 1 on the submersible
        clawAssembly.setWristPos(0.66);
        if (isStopRequested()) return;
        liftAssembly.zeroLiftOnly();
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        clawAssembly.setWristPos(0.45);
        liftAssembly.slideToEncoderPosBlocking(1565);//1563);
        //liftAssembly.slideToEncoderPos(1400);
        //sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        sleep(100);
        if (isStopRequested()) return;
        clawAssembly.clawClose();

        // Prepare to swipe samples into observation zone
        liftAssembly.liftToEncoderPos(-400);
        clawAssembly.setWristPos(0.54);
        sleep(100);
        Actions.runBlocking(new SequentialAction(driveFromSubmersibleToSampleOnePos));
        clawAssembly.clawOpenHuge();
        clawAssembly.setWristPos(0.71);
        liftAssembly.slideToEncoderPosBlocking(2800);
        //liftAssembly.slideToEncoderPos(2800);
        //if (isStopRequested()) return;
        //sleep(1000);
        //if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(deliverTwoSamplesToObservationZonePos));
        liftAssembly.liftToEncoderPos(-900);
        sleep(100);
        //liftAssembly.slideToEncoderPosBlocking(200);
        liftAssembly.slideToEncoderPos(200);
        clawAssembly.clawOpen();
        clawAssembly.setWristPos(0.77);
        sleep(200);
        Actions.runBlocking(new SequentialAction(driveFromObservationSampleTwoToSpecimenPos));

        // Grab specimen two off of field boarder
        clawAssembly.setWristPos(0.77);
        liftAssembly.liftToEncoderPos(-900);
        liftAssembly.slideToEncoderPosBlocking(200);
        //liftAssembly.slideToEncoderPos(200);
        //if (isStopRequested()) return;
        //sleep(100);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-2100);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSpecimenToSubmersibleTwoPos));

        // Deliver specimen two on submersible
        clawAssembly.setWristPos(0.47);
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPosBlocking(1700);
        //liftAssembly.slideToEncoderPos(1600);
        //if (isStopRequested()) return;
        //sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-900);
        clawAssembly.setWristPos(0.77);
        liftAssembly.slideToEncoderPos(200);
        if (isStopRequested()) return;
        sleep(100); // Dont decrease ever unless you want wheelie
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSubmersibleTwoToSpecimenPos));

        // Grab specimen three off of field
        clawAssembly.setWristPos(0.77);
        liftAssembly.liftToEncoderPos(-900);
        liftAssembly.slideToEncoderPos(200);
        if (isStopRequested()) return;
        sleep(600);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-2100);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSpecimenToSubmersibleThreePos));

        // Deliver specimen three on submersible
        clawAssembly.setWristPos(0.47);
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        //liftAssembly.liftToEncoderPos(-2600);
        //if (isStopRequested()) return;
        //sleep(100);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPosBlocking(1700);
        //liftAssembly.slideToEncoderPos(1600);
        //if (isStopRequested()) return;
        //sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-900);
        clawAssembly.setWristPos(0.77);
        liftAssembly.slideToEncoderPos(200);
        if (isStopRequested()) return;
        sleep(100); // Dont decrease ever unless you want wheelie
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSubmersibleThreeToSpecimenPos));

        // Grab specimen four four off of field boarder
        clawAssembly.setWristPos(0.77);
        liftAssembly.liftToEncoderPos(-900);
        liftAssembly.slideToEncoderPos(200);
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-2100);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSpecimenToSubmersibleFourPos));

        // Deliver specimen four on submersible
        clawAssembly.setWristPos(0.47);
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPosBlocking(1568);//1565);
        clawAssembly.setWristPos(0.70);
        //if (isStopRequested()) return;
        //sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.clawOpenHuge();
        sleep(100);
        if (isStopRequested()) return;
        //clawAssembly.setWristPos(0.54);
        clawAssembly.clawClose();
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-900);
        //clawAssembly.setWristPos(0.54);
        sleep(100);
        liftAssembly.slideToEncoderPos(2800);
        Actions.runBlocking(new SequentialAction(driveFromSubmersibleFourToParkPos));
        //sleep(1000);
    }
}
