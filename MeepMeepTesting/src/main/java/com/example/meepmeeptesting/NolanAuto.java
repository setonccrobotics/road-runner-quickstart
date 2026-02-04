package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class NolanAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Create a drive object for Meep Meep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .setDimensions(17,18)
                .build();
        DriveShim drive = myBot.getDrive();

        // Nolan's code goes here
        myBot.runAction(new SequentialAction(drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .lineToX(53)
                .lineToX(0)
                .build()));


        myBot.runAction(new SequentialAction(drive.actionBuilder(new Pose2d(-56, -44, Math.toRadians(235.00)))
                .lineToXConstantHeading(-34)
                .splineToSplineHeading(new Pose2d(-12, -30, Math.toRadians(270.00)), Math.toRadians(-37.37))
                .strafeToSplineHeading(new Vector2d(-12, -36), Math.toRadians(270.00))
                .strafeToSplineHeading(new Vector2d(-12, -42), Math.toRadians(270.00))
                .strafeToSplineHeading(new Vector2d(-12, -48), Math.toRadians(270.00))
                .splineToSplineHeading(new Pose2d(-36, -20, Math.toRadians(235.00)), Math.toRadians(114.00))
                .build()));


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}