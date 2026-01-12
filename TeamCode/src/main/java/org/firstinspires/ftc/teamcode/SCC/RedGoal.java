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
    @Override
    public void runOpMode() throws InterruptedException {
        // Define the field positions
        Pose2d startPos = new Pose2d(-58, 43, Math.toRadians(-54));
        Pose2d launchPosOne = new Pose2d(-35, 19.1, Math.toRadians(-54));
        Pose2d parkPos = new Pose2d(-25, 50, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        RobotConveyor robotConveyor = new RobotConveyor(hardwareMap);
        //RobotVision robotVision = new RobotVision();
        //RobotLift robotLift = new RobotLift(hardwareMap);

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
        //robotLift.zero();
        //robotVision.zero(hardwareMap);

        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;

        // Update the target position
        robotConveyor.updateTargetDistance(45);
        updateTelemetry(telemetry);
        if (isStopRequested()) return;
        sleep(200);

        //.Launch 3 balls - first turn on the launch motor
        robotConveyor.launchMotorOn();

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(driveFromStartToLaunchPosOne));

        if (isStopRequested()) return;

        int successfulBallLaunchCount = 0;
        while (successfulBallLaunchCount < 3) {
            if (isStopRequested()) return;
            robotConveyor.turnOutTakeOff();
            robotConveyor.turnInTakeOff();
            sleep(1000);
            if (launchBall(robotConveyor)) {
                successfulBallLaunchCount++;

                /*while (robotConveyor.getLaunchSensorDistance() > 4.0
                        && (successfulBallLaunchCount < 3)) {
                    robotConveyor.ballPickup();
                }sleep(1000);*/
                if (successfulBallLaunchCount < 3) {
                    robotConveyor.ballPickup();
                    sleep(2000);
                    robotConveyor.ballPickup();
                }
            }
        }
        robotConveyor.launchMotorOff();

        // Drive from the launch position to park position
        Actions.runBlocking(new SequentialAction(driveFromLaunchPosOneToPark));

        sleep(1000);
    }

    public boolean launchBall(RobotConveyor conveyor) {
        // Expects the launch motor to be controlled externally
        conveyor.launchBall();
        if (isStopRequested()) return false;
        sleep(2000);
        conveyor.launchGateOpen();
        if (isStopRequested()) return false;
        sleep(500);
        return true;
    }
}