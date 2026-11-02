package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

// Finds the goal AprilTag with a Limelight 3A and reports how far away it is.
// The Limelight pipeline must be an AprilTag pipeline with "Full 3D" enabled and the
// correct tag size set, otherwise the 3D target pose (and so the distance) is all zeros.
public class RobotLimelight {
    // AprilTag IDs on the goals; tags with any other ID are ignored.
    // Red scoring: 30, 31. TODO: add the blue scoring tag IDs.
    static final int[] GOAL_TAG_IDS = {30, 31};
    static final int PIPELINE = 0;

    private final Limelight3A limelight;
    private boolean hasTarget = false;
    private int tagId = 0;
    private double distance = 0.0;
    private double tx = 0.0;

    public RobotLimelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(PIPELINE);
        // Without start(), getLatestResult() returns null
        limelight.start();
    }

    // Call once per loop before reading the getters
    public void update() {
        hasTarget = false;
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid())
            return;

        for (LLResultTypes.FiducialResult fiducial : result.getFiducialResults()) {
            if (!isGoalTag(fiducial.getFiducialId()))
                continue;
            // Camera space: +x right, +y down, +z forward. Ignore height so this is
            // the distance across the floor from the camera to the tag.
            Position position = fiducial.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH);
            hasTarget = true;
            tagId = fiducial.getFiducialId();
            distance = Math.hypot(position.x, position.z);
            tx = fiducial.getTargetXDegrees();
            return;
        }
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    // Inches from the camera to the goal tag; keeps the last value when the tag is lost
    public double getDistance() {
        return distance;
    }

    // Degrees the tag is right (+) or left (-) of the crosshair
    public double getTx() {
        return tx;
    }

    public int getTagId() {
        return tagId;
    }

    public void stop() {
        limelight.stop();
    }

    public void addTelemetry(Telemetry telemetry) {
        if (hasTarget) {
            telemetry.addData("Goal tag", "ID %d  %.1f in  tx %.1f deg", tagId, distance, tx);
        } else {
            telemetry.addData("Goal tag", "not seen (last %.1f in)", distance);
        }
    }

    private static boolean isGoalTag(int id) {
        for (int goalId : GOAL_TAG_IDS) {
            if (id == goalId)
                return true;
        }
        return false;
    }
}
