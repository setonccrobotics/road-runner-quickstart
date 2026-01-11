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

@Autonomous(name="RedGoal", group="SCC")
public class RedGoal extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private RobotConveyor robotConveyor = new RobotConveyor(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        RobotVision robotVision = new RobotVision();
        RobotLift robotLift = new RobotLift(hardwareMap);

        // Define the field positions
        Pose2d startPos = new Pose2d(-58, 43, Math.toRadians(-54));
        Pose2d launchPosOne = new Pose2d(-35, 19.1, Math.toRadians(-54));
        Pose2d parkPos = new Pose2d(-25, 50, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .strafeTo(launchPosOne.position)
                .build();

        Action driveFromLaunchPosOneToPark = drive.actionBuilder(launchPosOne)
                .splineTo(parkPos.position, parkPos.heading)
                .build();

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // First thing first!! Zero the robot
        robotConveyor.zero();
        robotLift.zero();
        robotVision.zero(hardwareMap);

        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(driveFromStartToLaunchPosOne));

        if (isStopRequested()) return;

        // Update the target position
        robotConveyor.updateTargetDistance(robotVision);
        if (isStopRequested()) return;
        sleep(200);

        //.Launch 3 balls - first turn on the launch motor
        robotConveyor.launchMotorOn();
        int successfulBallLaunchCount = 0;
        while (successfulBallLaunchCount < 3) {
            if (isStopRequested()) return;
            if (launchBall())
                successfulBallLaunchCount++;
        }
        robotConveyor.launchMotorOff();

        // Drive from the launch position to park position
        Actions.runBlocking(new SequentialAction(driveFromLaunchPosOneToPark));

        sleep(5000);
    }

    public boolean launchBall() {
        // Expects the launch motor to be controlled externally
        robotConveyor.launchBall();
        if (isStopRequested()) return false;
        sleep(1000);
        robotConveyor.launchGateOpen();
        if (isStopRequested()) return false;
        sleep(1000);
        return robotConveyor.getLaunchSensorDistance() < 4.0;
    }
}
