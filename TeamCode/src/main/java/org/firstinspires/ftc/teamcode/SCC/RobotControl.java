package org.firstinspires.ftc.teamcode.SCC;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotControl {
    RobotConveyor robotConveyor;

    public RobotControl(HardwareMap hardwareMap) {
        robotConveyor = new RobotConveyor(hardwareMap);
    }

    public class LaunchMotorOn implements Action {
        @Override
        public boolean run (@NonNull TelemetryPacket packet) {
            robotConveyor.updateTargetDistance(2400);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            robotConveyor.launchMotorOn();
            return false;
        }
    }
    public Action launchMotorOn() {
        return new LaunchMotorOn();
    }

    public class LaunchMotorOff implements Action {
        @Override
        public boolean run (@NonNull TelemetryPacket packet) {
            robotConveyor.launchMotorOff();
            return false;
        }
    }
    public Action launchMotorOff() {
        return new LaunchMotorOff();
    }

    public class ConveyorOn implements Action {
        @Override
        public boolean run (@NonNull TelemetryPacket packet) {
            robotConveyor.ballPickup();
            /*robotConveyor.launchMotorOn();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }
            robotConveyor.launchMotorOff();*/
            return false;
        }
    }
    public Action conveyorOn() {
        return new ConveyorOn();
    }

    public class ConveyorOff implements Action {
        @Override
        public boolean run (@NonNull TelemetryPacket packet) {
            robotConveyor.turnInTakeOff();
            robotConveyor.turnOutTakeOff();
            return false;
        }
    }
    public Action conveyorOff() {
        return new ConveyorOff();
    }

    public class LaunchBalls implements Action {
        @Override
        public boolean run (@NonNull TelemetryPacket packet) {
            robotConveyor.updateTargetDistance(45);
            robotConveyor.launchMotorOn();
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                return false;
            }

            int ballCount = 0;
            while (ballCount < 3) {
                // Launch ball one
                robotConveyor.launchGateOpen();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return false;
                }
                // Make sure a ball is in place to launch
                while (robotConveyor.getLaunchSensorDistance() > 8.0) {
                    robotConveyor.ballPickup();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        return false;
                    }
                }
                // Launch the ball
                robotConveyor.ballBackup();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return false;
                }
                robotConveyor.turnOutTakeOff();
                robotConveyor.turnInTakeOff();
                robotConveyor.launchBall();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return false;
                }
                // End launch ball one
                ballCount++;
            }
            robotConveyor.launchMotorOff();
            return false;
        }
    }
    public Action launchBalls() {
        return new LaunchBalls();
    }
}
