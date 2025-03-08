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
        Pose2d submersiblePos = new Pose2d(9, -43.25, Math.toRadians(270.0));
        Pose2d submersibleTwoPos = new Pose2d(6, -42, Math.toRadians(270.0));
        Pose2d submersibleThreePos = new Pose2d(3, -41.75, Math.toRadians(270.0));
        Pose2d submersibleFourPos = new Pose2d(0, -42, Math.toRadians(270.0));
        Pose2d specimenPos = new Pose2d(34, -55, Math.toRadians(270.0));
        Pose2d sampleOnePos = new Pose2d(26, -44, Math.toRadians(60.0));
        Pose2d sampleTwoPos = new Pose2d(38, -44, Math.toRadians(60.0));
        Pose2d observationSampleOnePos = new Pose2d(25, -44, Math.toRadians(-43.0));
        Pose2d observationSampleTwoPos = new Pose2d(38.5, -44, Math.toRadians(-43.0));
        Pose2d parkPos = new Pose2d(45, -55, Math.toRadians(-50.0));

        // Create a drive object for meep meep
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
                .build();
        DriveShim drive = myBot.getDrive();

        // Define the robot actions
        Action driveFromStartToSubmersiblePos = drive.actionBuilder(startPos)
                .lineToY(submersiblePos.position.y)
                .build();

        Action driveFromSubmersibleToSampleOnePos = drive.actionBuilder(submersiblePos)
                .splineTo(new Vector2d(sampleOnePos.position.x, sampleOnePos.position.y), sampleOnePos.heading)
                .build();

        Action deliverTwoSamplesToObservationZonePos = drive.actionBuilder(sampleOnePos)
                .splineToLinearHeading(observationSampleOnePos, observationSampleOnePos.heading)
                .splineToLinearHeading(sampleOnePos, sampleOnePos.heading)
                .splineToLinearHeading(sampleTwoPos, sampleTwoPos.heading)
                .splineToLinearHeading(observationSampleTwoPos, observationSampleTwoPos.heading)
                .build();

        Action driveFromObservationSampleTwoToSpecimenPos = drive.actionBuilder(observationSampleTwoPos)
                .turnTo(specimenPos.heading)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSpecimenToSubmersibleTwoPos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleTwoPos.position.x, submersibleTwoPos.position.y))
                .build();

        Action driveFromSubmersibleTwoToSpecimenPos = drive.actionBuilder(submersibleTwoPos)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSpecimenToSubmersibleThreePos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleThreePos.position.x, submersibleThreePos.position.y))
                .build();

        Action driveFromSubmersibleThreeToSpecimenPos = drive.actionBuilder(submersibleThreePos)
                .splineToLinearHeading(specimenPos, specimenPos.heading)
                .build();

        Action driveFromSpecimenToSubmersibleFourPos = drive.actionBuilder(specimenPos)
                .strafeTo(new Vector2d(submersibleFourPos.position.x, submersibleFourPos.position.y))
                .build();

        Action driveFromSubmersibleFourToParkPos = drive.actionBuilder(submersibleFourPos)
                //.strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                .turnTo(parkPos.heading)
                .strafeTo(new Vector2d(parkPos.position.x, parkPos.position.y))
                //.splineTo(new Vector2d(parkPos.position.x, parkPos.position.y), parkPos.heading)
                .build();


        // Define the order of actions
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

        // Run the auto program
        myBot.runAction(runAuto);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}