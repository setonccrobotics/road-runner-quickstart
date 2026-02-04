package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="BlueLong", group="SCC")
public class BlueLong extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // Define the field positions
        Pose2d startPos = new Pose2d(61, -8, Math.toRadians(0));
        Pose2d launchPosOne = new Pose2d(53, -12, Math.toRadians(22.5));
        Pose2d parkPos = new Pose2d(44, -14, Math.toRadians(40));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        RobotConveyor robotConveyor = new RobotConveyor(hardwareMap);
        RobotVision robotVision = new RobotVision();

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .lineToX(launchPosOne.position.x)
                .splineToLinearHeading(launchPosOne, launchPosOne.heading)
                .build();

        Action driveFromLaunchPosOneToPark = drive.actionBuilder(launchPosOne)
                .splineToLinearHeading(parkPos, parkPos.heading)
                .build();

        //Action autoLaunchBall = RobotConveyor.AutoLaunchBall;

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // First thing first!! Zero the robot
        robotConveyor.zero();
        //robotVision.zero(hardwareMap);

        if (isStopRequested()) return;
        sleep(200);
        if (isStopRequested()) return;

        //Actions.runBlocking(new SequentialAction(RobotConveyor.AutoLaunchBall));
        sleep(4000);

        /*
        // Launch 3 balls - first turn on the launch motor
        robotConveyor.updateTargetDistance(2600);
        robotConveyor.launchMotorOn();

        // Drive from the start position to the launch position
        Actions.runBlocking(new SequentialAction(driveFromStartToLaunchPosOne));

        // Launch 3 balls
        int successfulBallLaunchCount = 0;
        while (successfulBallLaunchCount < 3) {
            if (isStopRequested()) return;
            robotConveyor.turnOutTakeOff();
            robotConveyor.turnInTakeOff();
            sleep(1000);
            robotConveyor.ballBackup();
            sleep(200);
            robotConveyor.turnOutTakeOff();
            robotConveyor.turnInTakeOff();
            sleep(200);
            if (launchBall(robotConveyor)) {
                successfulBallLaunchCount++;

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

        sleep(1000);*/
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