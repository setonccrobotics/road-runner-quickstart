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

@Autonomous(name="ObservationZone", group="SCC")
@Disabled
public class ObservationZone extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        LiftAssembly liftAssembly = new LiftAssembly(hardwareMap);
        OverheadLift overheadLift = new OverheadLift(hardwareMap);
        ClawAssembly clawAssembly = new ClawAssembly(hardwareMap);

        // Define the field positions
        Pose2d startPos = new Pose2d(0, 0, Math.toRadians(90.0));
        Pose2d submersiblePos = new Pose2d(0, 26, Math.toRadians(90.0));
        Pose2d startOffWallPos = new Pose2d(0, 5, Math.toRadians(90.0));
        Pose2d parkPos = new Pose2d(36, 4, Math.toRadians(90.0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToStartOffWallPos = drive.actionBuilder(startPos)
                .lineToY(startOffWallPos.position.y)
                .build();

        Action driveFromStartOffWallToSubmersiblePos = drive.actionBuilder(startOffWallPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromSubmersibleToStartOffWallPos = drive.actionBuilder(submersiblePos)
                .lineToY(startOffWallPos.position.y)
                .build();

        Action driveFromStartOffWallToParkPos = drive.actionBuilder(startOffWallPos)
                .strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
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

        Actions.runBlocking(new SequentialAction(driveFromSubmersibleToStartOffWallPos));

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        Actions.runBlocking(new SequentialAction(driveFromStartOffWallToParkPos));

        if (isStopRequested()) return;
        sleep(100);
        if (isStopRequested()) return;

        liftAssembly.zero();
        overheadLift.zero();
        clawAssembly.zero();
        liftAssembly.liftToHomePos();

        if (isStopRequested()) return;
    }
}
