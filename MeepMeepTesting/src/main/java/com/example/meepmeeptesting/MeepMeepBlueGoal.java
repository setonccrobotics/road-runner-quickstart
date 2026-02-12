package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepBlueGoal {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Define the field positions
        Pose2d startPos = new Pose2d(-58, -43, Math.toRadians(54));
        Pose2d launchPosOne = new Pose2d(-35, -16.1, Math.toRadians(54));
        Pose2d tapeMarkOnePos = new Pose2d(-12, -28, Math.toRadians(-90));
        Pose2d tapeMark1APos = new Pose2d(-12, -33, Math.toRadians(-90));
        Pose2d tapeMark1BPos = new Pose2d(-12, -38, Math.toRadians(-90));
        Pose2d tapeMark1CPos = new Pose2d(-12, -43, Math.toRadians(-90));

        // Create a drive object for meep meep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .setDimensions(17,18)
                .build();
        DriveShim drive = myBot.getDrive();

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .strafeTo(launchPosOne.position)
                .build();

        Action driveFromLaunchPosOneToTapeMarkOne = drive.actionBuilder(launchPosOne)
                .splineTo(tapeMarkOnePos.position, tapeMarkOnePos.heading)
                .build();

        Action driveFromTapeMarkOneTo1A = drive.actionBuilder(tapeMarkOnePos)
                .splineTo(tapeMark1APos.position, tapeMark1APos.heading)
                .build();

        Action driveFromTapeMark1ATo1B = drive.actionBuilder(tapeMark1APos)
                .splineTo(tapeMark1BPos.position, tapeMark1BPos.heading)
                .build();

        Action driveFromTapeMark1BTo1C = drive.actionBuilder(tapeMark1BPos)
                .splineTo(tapeMark1CPos.position, tapeMark1CPos.heading)
                .build();

        Action driveFromTapeMark1CToLaunchPosOne = drive.actionBuilder(tapeMark1CPos)
                .splineTo(launchPosOne.position, launchPosOne.heading)
                .build();


        // Define the order of actions
        Action runAuto = new SequentialAction(
                driveFromStartToLaunchPosOne,
                driveFromLaunchPosOneToTapeMarkOne,
                driveFromTapeMarkOneTo1A,
                driveFromTapeMark1ATo1B,
                driveFromTapeMark1BTo1C,
                driveFromTapeMark1CToLaunchPosOne);

        // Run the auto program
        myBot.runAction(runAuto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}