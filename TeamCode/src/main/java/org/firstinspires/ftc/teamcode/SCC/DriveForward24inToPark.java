package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="DriveForward24inToPark", group="SCC")
public class DriveForward24inToPark extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Define the field positions
        Pose2d startPos = new Pose2d(0.0, 0.0, Math.toRadians(0.0));
        Pose2d parkPos = new Pose2d(24.0, 0.0, Math.toRadians(0.0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToPark = drive.actionBuilder(startPos)
                .lineToX(parkPos.position.x)
                .build();

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // Drive from the start position to the park position
        Actions.runBlocking(new SequentialAction(driveFromStartToPark));

        if (isStopRequested()) return;

        sleep(1000);
    }
}