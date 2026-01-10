package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepRedGoal {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Define the field positions
        Pose2d startPos = new Pose2d(-58, 43, Math.toRadians(-54));
        Pose2d launchPosOne = new Pose2d(-35, 19.1, Math.toRadians(-54));
        Pose2d parkPos = new Pose2d(-25, 50, Math.toRadians(90));

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

        Action driveFromLaunchPosOneToPark = drive.actionBuilder(launchPosOne)
                .splineTo(parkPos.position, parkPos.heading)
                .build();


        // Define the order of actions
        Action runAuto = new SequentialAction(
                driveFromStartToLaunchPosOne,
                driveFromLaunchPosOneToPark);

        // Run the auto program
        myBot.runAction(runAuto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}