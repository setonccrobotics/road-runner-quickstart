package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="ObservationTripleChamber", group="SCC")
public class ObservationTripleChamber extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        LiftAssembly liftAssembly = new LiftAssembly(hardwareMap);
        OverheadLift overheadLift = new OverheadLift(hardwareMap);
        ClawAssembly clawAssembly = new ClawAssembly(hardwareMap);

        // Define the field positions
        /*Pose2d startPos = new Pose2d(0, 0, Math.toRadians(270.0));
        Pose2d startOffWallPos = new Pose2d(0, 8, Math.toRadians(270.0));
        Pose2d sampleOnePos = new Pose2d(39.5, 50, Math.toRadians(270.0));
        Pose2d parkPos = new Pose2d(36, 6, Math.toRadians(270.0));
        Pose2d specimenPos = new Pose2d(39.5, 6.5, Math.toRadians(270.0));
        Pose2d specimenThreePos = new Pose2d(38.5, 6.5, Math.toRadians(270.0));
        Pose2d submersiblePos = new Pose2d(0, 17, Math.toRadians(270.0));
        Pose2d submersibleTwoPos = new Pose2d(-3, 15.5, Math.toRadians(270.0));
        Pose2d submersibleThreePos = new Pose2d(-6, 15.5, Math.toRadians(270.0));*/
        Pose2d startPos = new Pose2d(9, -61, Math.toRadians(270.0));
        Pose2d startOffWallPos = new Pose2d(9, -53, Math.toRadians(270.0));
        Pose2d sampleOnePos = new Pose2d(48.5, -11, Math.toRadians(270.0));
        Pose2d parkPos = new Pose2d(45, -55, Math.toRadians(270.0));
        Pose2d specimenPos = new Pose2d(48.5, -54.5, Math.toRadians(270.0));
        Pose2d specimenThreePos = new Pose2d(47.5, -54.5, Math.toRadians(270.0));
        Pose2d submersiblePos = new Pose2d(9, -44, Math.toRadians(270.0));
        Pose2d submersibleTwoPos = new Pose2d(6, -42.5, Math.toRadians(270.0));
        Pose2d submersibleThreePos = new Pose2d(3, -42.5, Math.toRadians(270.0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToStartOffWallPos = drive.actionBuilder(startPos)
                .lineToY(startOffWallPos.position.y)
                .build();

        Action driveFromStartOffWallToSubmersiblePos = drive.actionBuilder(startOffWallPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromStartToSubmersiblePos = drive.actionBuilder(startPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromSubmersibleThreeToParkPos = drive.actionBuilder(submersibleThreePos)
                //.lineToY(parkPos.position.y)
                //.strafeTo(new Vector2d(parkPos.position.x, submersiblePos.position.y - 10))
                //.splineToLinearHeading(parkPos, parkPos.heading)
                .strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                .build();

        Action driveFromSubmersibleToSampleOnePos = drive.actionBuilder(submersiblePos)
                .strafeTo(new Vector2d(submersiblePos.position.x + 8, submersiblePos.position.y))
                .splineToLinearHeading(sampleOnePos, sampleOnePos.heading)
                //.splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSampleOnePosToSpecimenPos = drive.actionBuilder(sampleOnePos)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSubmersibleTwoToSpecimenPos = drive.actionBuilder(submersibleTwoPos)
                //.lineToY(submersibleTwoPos.position.y - 12)
                .strafeTo(new Vector2d(specimenThreePos.position.x, specimenThreePos.position.y))
                //.splineToLinearHeading(specimenThreePos, specimenThreePos.heading)
                .build();

        Action driveFromSpecimenToSubmersiblePos = drive.actionBuilder(specimenPos)
                .lineToY(specimenPos.position.y + 6)
                //.splineToLinearHeading(submersibleTwoPos, submersibleTwoPos.heading)
                //.lineToY(submersibleTwoPos.position.y + 2)
                .strafeTo(new Vector2d(submersibleTwoPos.position.x, submersibleTwoPos.position.y))
                .build();

        Action driveFromSpecimenThreeToSubmersibleThreePos = drive.actionBuilder(specimenThreePos)
                //.lineToY(specimenThreePos.position.y + 2)
                //.splineToLinearHeading(submersibleThreePos, submersibleThreePos.heading)
                //.lineToY(submersibleThreePos.position.y + 2)
                .strafeTo(new Vector2d(submersibleThreePos.position.x, submersibleThreePos.position.y))
                .build();

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

        clawAssembly.zero();
        liftAssembly.zero();
        liftAssembly.extendStabilityServo();
        clawAssembly.setWristPos(0.66);
        if (isStopRequested()) return;
        liftAssembly.zeroLiftOnly();
        //if (isStopRequested()) return;
        //liftAssembly.liftToEncoderPos(-800);
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        clawAssembly.setWristPos(0.48);

        // Drive up to the spike mark
        //Actions.runBlocking(new SequentialAction(driveFromStartOffWallToSubmersiblePos));
        //if (isStopRequested()) return;

        liftAssembly.slideToEncoderPos(1600);
        if (isStopRequested()) return;
        sleep(1200);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-700);
        clawAssembly.setWristPos(0.7);
        liftAssembly.slideToEncoderPos(171);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSubmersibleToSampleOnePos));

        Actions.runBlocking(new SequentialAction(driveFromSampleOnePosToSpecimenPos));

        // Grab specimen off of field boarder
        liftAssembly.liftToEncoderPos(-700);
        liftAssembly.slideToEncoderPos(171);
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-1500);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromSpecimenToSubmersiblePos));

        // Deliver specimen on submersible
        clawAssembly.setWristPos(0.48);
        //if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPos(1600);
        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        if (isStopRequested()) return;
        //sleep(1000);
        //if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-700);
        clawAssembly.setWristPos(0.7);
        liftAssembly.slideToEncoderPos(171);
        if (isStopRequested()) return;
        sleep(800);

        Actions.runBlocking(new SequentialAction(driveFromSubmersibleTwoToSpecimenPos));

        // Grab specimen off of field boarder
        liftAssembly.liftToEncoderPos(-700);
        liftAssembly.slideToEncoderPos(171);
        if (isStopRequested()) return;
        sleep(600);
        if (isStopRequested()) return;
        clawAssembly.clawClose();
        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-1500);
        if (isStopRequested()) return;
        sleep(300);
        if (isStopRequested()) return;


        Actions.runBlocking(new SequentialAction(driveFromSpecimenThreeToSubmersibleThreePos));

        // Deliver specimen on submersible
        clawAssembly.setWristPos(0.48);
        if (isStopRequested()) return;
        sleep(100);
        //if (isStopRequested()) return;
        liftAssembly.liftToTop();
        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;
        liftAssembly.slideToEncoderPos(1600);
        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;
        clawAssembly.clawOpen();
        //if (isStopRequested()) return;
        //sleep(1000);
        Actions.runBlocking(new ParallelAction(driveFromSubmersibleThreeToParkPos));
        if (isStopRequested()) return;
        liftAssembly.liftToEncoderPos(-700);
        clawAssembly.setWristPos(0.7);
        liftAssembly.slideToEncoderPos(171);
    }
}
