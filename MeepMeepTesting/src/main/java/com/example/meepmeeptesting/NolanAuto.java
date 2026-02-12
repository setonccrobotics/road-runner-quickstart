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
        //myBot.runAction(new SequentialAction(drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
        //        .lineToX(53)
        //        .lineToX(0)
        //        .build()));


        myBot.runAction(new SequentialAction(drive.actionBuilder(new Pose2d(-62.0, -22.0, Math.toRadians(0.00)))
                //.splineToSplineHeading(new Pose2d(-22.0, -22.0, Math.toRadians(44.0)), Math.toRadians(0.0))
                .lineToXLinearHeading(-22, 44)
                //.strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0))
                .strafeToSplineHeading(new Vector2d(-12.0, -30.0), Math.toRadians(270.0))
                .strafeToConstantHeading(new Vector2d(-12.0, -54.0))
                .strafeToConstantHeading(new Vector2d(-12.0, -50.0))
                .splineToSplineHeading(new Pose2d(-4.0, -55.0, Math.toRadians(180.0)), Math.toRadians(270.0))
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0))
                .strafeToSplineHeading(new Vector2d(12.0, -30.0), Math.toRadians(270.0))
                .strafeToConstantHeading(new Vector2d(12.0, -50.0))
                .strafeToSplineHeading(new Vector2d(-22.0, -22.0), Math.toRadians(44.0))
                .build()));


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}