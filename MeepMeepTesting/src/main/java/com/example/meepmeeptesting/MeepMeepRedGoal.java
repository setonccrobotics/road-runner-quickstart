package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepRedGoal {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Create a drive object for meep meep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .setDimensions(17,18)
                .build();
        DriveShim drive = myBot.getDrive();

        // Define the robot actions
        myBot.runAction(new SequentialAction(drive.actionBuilder(new Pose2d(-62, 33, Math.toRadians(270)))
                //Go from start position to Launch position
                .strafeToSplineHeading(new Vector2d(-24.0, 20.0), Math.toRadians(308.0))
                //Go to before the balls
                .strafeToSplineHeading(new Vector2d(-11.0, 30.0), Math.toRadians(70.0))
                //Go thrugh the balls
                .strafeToConstantHeading(new Vector2d(-11.0, 54.0))
                //Go back and open gate
                .strafeToConstantHeading(new Vector2d(-11.0, 50.0))
                .splineToSplineHeading(new Pose2d(-4.0, 55.0, Math.toRadians(180.0)), Math.toRadians(90.0))
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-22.0, 22.0), Math.toRadians(314.0))
                //go to other line of balls
                .strafeToSplineHeading(new Vector2d(13.0, 30.0), Math.toRadians(90.0))
                //go forward thrugh balls
                .strafeToConstantHeading(new Vector2d(13.0, 50.0))
                //go to launch position
                .strafeToSplineHeading(new Vector2d(-22.0, 22.0), Math.toRadians(314.0))
                .build()));


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}