package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="BlueGoal", group="SCC")
public class BlueLong extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Define the field positions
        Pose2d startPos = new Pose2d(61, -8, Math.toRadians(180));
        Pose2d launchPosOne = new Pose2d(53, -12, Math.toRadians(210));
        Pose2d parkPos = new Pose2d(24, -46, Math.toRadians(-90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        RobotConveyor robotConveyor = new RobotConveyor(hardwareMap);
        RobotVision robotVision = new RobotVision();
        //RobotLift robotLift = new RobotLift(hardwareMap);

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .splineTo(launchPosOne.position,launchPosOne.heading)
                .build();

        //Action driveFromLaunchPosOneToPark = drive.actionBuilder(launchPosOne)
                //.splineTo(parkPos.position, parkPos.heading)
                //.build();

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
        while(robotVision.getLeftOffset() > 0.2 || robotVision.getLeftOffset() < -0.2) {
            robotConveyor.updateTargetDistance(robotVision);
        }
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
        //Actions.runBlocking(new SequentialAction(driveFromLaunchPosOneToPark));

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