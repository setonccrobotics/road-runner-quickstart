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
        Pose2d startPos = new Pose2d(9, -61, Math.toRadians(270.0));
        Pose2d startOffWallPos = new Pose2d(9, -53, Math.toRadians(270.0));
        Pose2d sampleOnePos = new Pose2d(48.5, -11, Math.toRadians(270.0));
        Pose2d sampleTwoPos = new Pose2d(59, -11, Math.toRadians(270.0));
        Pose2d parkPos = new Pose2d(45, -55, Math.toRadians(270.0));
        Pose2d specimenPos = new Pose2d(48.5, -54.5, Math.toRadians(270.0));
        Pose2d specimenThreePos = new Pose2d(47.5, -54.5, Math.toRadians(270.0));
        Pose2d submersiblePos = new Pose2d(9, -44, Math.toRadians(270.0));
        Pose2d submersibleTwoPos = new Pose2d(6, -42.5, Math.toRadians(270.0));
        Pose2d submersibleThreePos = new Pose2d(3, -42.5, Math.toRadians(270.0));
        Pose2d submersibleFourPos = new Pose2d(0, -42.5, Math.toRadians(270.0));

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

        Action driveFromStartOffWallToSubmersiblePos = drive.actionBuilder(startOffWallPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromStartToSubmersiblePos = drive.actionBuilder(startPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromSubmersibleThreeToParkPos = drive.actionBuilder(submersibleThreePos)
                //.lineToY(parkPos.position.y)
                //.strafeTo(new Vector2d(parkPos.position.x, submersiblePos.position.y - 10))
                //.splineToLinearHeading(parkPos, parkPos.heading)
                .strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                .build();

        Action driveFromSubmersibleToSampleOnePos = drive.actionBuilder(submersiblePos)
                .strafeTo(new Vector2d(submersiblePos.position.x + 8, submersiblePos.position.y))
                .splineToLinearHeading(sampleOnePos, sampleOnePos.heading)
                //.splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSubmersibleTwoToSampleTwoPos = drive.actionBuilder(submersibleTwoPos)
                .strafeTo(new Vector2d(submersibleTwoPos.position.x + 8, submersibleTwoPos.position.y))
                .splineToLinearHeading(sampleTwoPos, sampleTwoPos.heading)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSampleOnePosToSpecimenPos = drive.actionBuilder(sampleOnePos)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSubmersibleTwoToSpecimenPos = drive.actionBuilder(submersibleTwoPos)
                //.lineToY(submersibleTwoPos.position.y - 12)
                //.strafeTo(new Vector2d(specimenThreePos.position.x, specimenThreePos.position.y))
                .splineToLinearHeading(specimenThreePos, specimenThreePos.heading)
                .build();

        Action driveFromSpecimenToSubmersiblePos = drive.actionBuilder(specimenPos)
                .lineToY(specimenPos.position.y + 6)
                //.splineToLinearHeading(submersibleTwoPos, submersibleTwoPos.heading)
                //.lineToY(submersibleTwoPos.position.y + 2)
                .strafeTo(new Vector2d(submersibleTwoPos.position.x, submersibleTwoPos.position.y))
                .build();

        Action driveFromSpecimenThreeToSubmersibleThreePos = drive.actionBuilder(specimenThreePos)
                //.lineToY(specimenThreePos.position.y + 6)
                //.strafeTo(new Vector2d(submersibleThreePos.position.x, submersibleThreePos.position.y))
                .splineToConstantHeading(new Vector2d(submersibleThreePos.position.x, submersibleThreePos.position.y),
                        submersibleThreePos.heading)
                //.lineToY(submersibleThreePos.position.y + 2)
                .build();

        Action driveFromSpecimenFourToSubmersibleFourPos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleFourPos.position.x, submersibleFourPos.position.y))
                .build();


        // Define the order of actions
        Action runAuto = new SequentialAction(
                driveFromStartToSubmersiblePos,
                //driveFromStartToStartOffWallPos,
                //driveFromStartOffWallToSubmersiblePos,
                driveFromSubmersibleToSampleOnePos,
                driveFromSampleOnePosToSpecimenPos,
                driveFromSpecimenToSubmersiblePos,
                driveFromSubmersibleTwoToSampleTwoPos,
                driveFromSpecimenThreeToSubmersibleThreePos,
                driveFromSubmersibleTwoToSpecimenPos,
                driveFromSpecimenFourToSubmersibleFourPos,
                driveFromSubmersibleThreeToParkPos);

        // Run the auto program
        myBot.runAction(runAuto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}