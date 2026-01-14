package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepBlueLong {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Create a drive object for meep meep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .setDimensions(17,18)
                .build();
        DriveShim drive = myBot.getDrive();

        // Define the field positions
        Pose2d startPos = new Pose2d(61, -8, Math.toRadians(180));
        Pose2d launchPosOne = new Pose2d(53, -12, Math.toRadians(210));
        Pose2d parkPos = new Pose2d(24, -46, Math.toRadians(-90));
        Pose2d pushhuman= new Pose2d(52, -53, Math.toRadians(-90));

        // Define the robot actions
        Action driveFromStartToLaunchPosOne = drive.actionBuilder(startPos)
                .splineTo(launchPosOne.position, launchPosOne.heading)
                .build();

        Action driveFromLaunchPosOneToPark = drive.actionBuilder(launchPosOne)
                .splineTo(parkPos.position, parkPos.heading)
                .build();
        Action driveFromStartTopushhuman = drive.actionBuilder(parkPos)
                .strafeTo(pushhuman.position)
                .build();

        // Define the order of actions
        Action runAuto = new SequentialAction(
                driveFromStartToLaunchPosOne,
                driveFromLaunchPosOneToPark,
                driveFromStartTopushhuman);

        // Run the auto program
        myBot.runAction(runAuto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}