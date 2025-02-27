package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // Define the field positions
        Pose2d startPos = new Pose2d(-24, 0, Math.toRadians(90.0));
        Pose2d startOffWallPos = new Pose2d(-24, 4, Math.toRadians(90.0));
        Pose2d netZonePos = new Pose2d(-41, 11, Math.toRadians(45.0));
        Pose2d tapeMark1 = new Pose2d(-39,29, Math.toRadians(90.0));
        Pose2d tapeMark2 = new Pose2d(-50,29, Math.toRadians(90.0));
        Pose2d tapeMark3 = new Pose2d(-53,29, Math.toRadians(125.0));

        // Create a drive object for meep meep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .build();
        DriveShim drive = myBot.getDrive();

        // Define the robot actions
        Action driveFromStartToStartOffWallPos = drive.actionBuilder(startPos)
                .lineToY(startOffWallPos.position.y)
                .build();

        Action driveFromStartOffWallToNetPos = drive.actionBuilder(startOffWallPos)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        Action driveFromNetToTapeMark1Pos = drive.actionBuilder(netZonePos)
                .turnTo(tapeMark1.heading)
                .splineToLinearHeading(tapeMark1, tapeMark1.heading)
                .build();

        Action driveFromTapeMark1ToNetZonePos = drive.actionBuilder(tapeMark1)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        Action driveFromNetToTapeMark2Pos = drive.actionBuilder(netZonePos)
                .turnTo(tapeMark2.heading)
                .splineToLinearHeading(tapeMark2, tapeMark2.heading)
                .build();

        Action driveFromTapeMark2ToNetZonePos = drive.actionBuilder(tapeMark2)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        Action driveFromNetToTapeMark3Pos = drive.actionBuilder(netZonePos)
                .turnTo(tapeMark3.heading)
                .splineToLinearHeading(tapeMark3, tapeMark3.heading)
                .build();

        Action driveFromTapeMark3ToNetZonePos = drive.actionBuilder(tapeMark3)
                .splineToLinearHeading(netZonePos, netZonePos.heading)
                .build();

        // Define the order of actions
        Action runAuto = new SequentialAction(
                driveFromStartToStartOffWallPos,
                driveFromStartOffWallToNetPos,
                driveFromNetToTapeMark1Pos,
                driveFromTapeMark1ToNetZonePos,
                driveFromNetToTapeMark2Pos,
                driveFromTapeMark2ToNetZonePos,
                driveFromNetToTapeMark3Pos,
                driveFromTapeMark3ToNetZonePos);
        /*/ Define the order of actions
        Action runAuto = new SequentialAction(
                driveFromStartToSubmersiblePos,
                driveFromSubmersibleToSampleOnePos,
                deliverTwoSamplesToObservationZonePos,
                driveFromObservationSampleTwoToSpecimenPos,
                driveFromSpecimenToSubmersibleTwoPos,
                driveFromSubmersibleTwoToSpecimenPos,
                driveFromSpecimenToSubmersibleThreePos,
                driveFromSubmersibleThreeToSpecimenPos,
                driveFromSpecimenToSubmersibleFourPos,
                driveFromSubmersibleFourToParkPos);
         */

        // Run the auto program
        myBot.runAction(runAuto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}