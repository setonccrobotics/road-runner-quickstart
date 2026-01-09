package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="BlueGoal", group="SCC")
@Disabled
public class BlueGoal extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        RobotVision robotVision = new RobotVision();
        McWinnerConveyor mcWinnerConveyor = new McWinnerConveyor(hardwareMap);
        RobotLift robotLift = new RobotLift(hardwareMap);

        // Define the field positions
        Pose2d startPos = new Pose2d(-58, -43, Math.toRadians(54));
        Pose2d launchPosOne = new Pose2d(-58.7, -16.1, Math.toRadians(54));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .lineToY(launchPosOne.position.y)
                .build();

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // First thing first!! Zero the robot
        mcWinnerConveyor.zero();
        robotLift.zero();
        robotVision.zero(hardwareMap);

        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(driveFromStartToLaunchPosOne));

        if (isStopRequested()) return;

        // Update the target position
        mcWinnerConveyor.updateTargetDistance(robotVision);
        if (isStopRequested()) return;
        sleep(200);
        // Launch the first ball
        mcWinnerConveyor.launchMotorOn();
        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;
        mcWinnerConveyor.launchBall();
        if (isStopRequested()) return;
        sleep(1000);
        mcWinnerConveyor.launchMotorOff();
        if (isStopRequested()) return;
        sleep(100);
        mcWinnerConveyor.launchGateOpen();
        if (isStopRequested()) return;
        sleep(1000);
        // Advance the next ball
        mcWinnerConveyor.turnConveyorOn();
        if (isStopRequested()) return;
        sleep(1000);
        mcWinnerConveyor.turnConveyorOff();
        if (isStopRequested()) return;
        sleep(100);


        // Launch the second ball
        mcWinnerConveyor.launchMotorOn();
        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;
        mcWinnerConveyor.launchBall();
        if (isStopRequested()) return;
        sleep(1000);
        mcWinnerConveyor.launchMotorOff();
        if (isStopRequested()) return;
        sleep(100);
        mcWinnerConveyor.launchGateOpen();
        if (isStopRequested()) return;
        sleep(1000);
        // Advance the next ball
        mcWinnerConveyor.turnConveyorOn();
        if (isStopRequested()) return;
        sleep(1000);
        mcWinnerConveyor.turnConveyorOff();
        if (isStopRequested()) return;
        sleep(100);


        sleep(5000);
    }
}
