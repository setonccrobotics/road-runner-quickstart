package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// McNeo: this season's robot.
//   Right bumper     : toggle AUTO thrower speed from the Limelight's distance to the goal
//   D-pad up/down    : thrower motor 1 speed +/- one step (switches back to manual)
//   D-pad left/right : thrower motor 2 speed -/+ one step (switches back to manual)
//   A                : start / stop both thrower motors (targets are kept)
//   B                : reset both thrower targets to zero
//   X / Y            : reverse thrower motor 1 / motor 2 (also stops the motors)
//   Right stick X    : rotate the turret
@TeleOp(name="McNeo", group="SCC")
public class McNeo extends LinearOpMode {
    // Speeds are in encoder ticks per second, same units as RobotConveyor's launcher
    static final double VELOCITY_STEP = 50.0;
    static final double MAX_VELOCITY = 3000.0;
    // Full stick deflection gives this much turret power
    static final double TURRET_MAX_POWER = 0.5;

    // Distance-to-speed table for AUTO mode, measured on the field. To add a row:
    // in manual mode, park at a distance, dial in speeds with the D-pad until shots land,
    // then add a row using the distance shown on telemetry. Distances must be increasing.
    // Between rows the speed is interpolated; outside the table the end row is used.
    static final double[] DISTANCES_IN   = {  42.0,   56.0,   61.0};
    static final double[] MOTOR1_SPEEDS  = { 550.0,  600.0,  500.0};
    static final double[] MOTOR2_SPEEDS  = {1050.0, 1450.0, 1700.0};

    private DcMotorEx throwerMotor1 = null;
    private DcMotorEx throwerMotor2 = null;
    private DcMotorEx turretMotor = null;
    private RobotLimelight limelight = null;

    @Override
    public void runOpMode() {
        throwerMotor1 = hardwareMap.get(DcMotorEx.class, "throwerMotor1");
        throwerMotor2 = hardwareMap.get(DcMotorEx.class, "throwerMotor2");
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        limelight = new RobotLimelight(hardwareMap);

        // Starting directions; X / Y flip them while the OpMode runs
        throwerMotor1.setDirection(DcMotor.Direction.FORWARD);
        throwerMotor2.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotorEx motor : new DcMotorEx[] {throwerMotor1, throwerMotor2}) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setVelocity(0.0);
        }

        // Turret holds its heading when the stick is released.
        // Encoder is zeroed at init, so start with the turret facing forward.
        turretMotor.setDirection(DcMotor.Direction.FORWARD);
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turretMotor.setPower(0.0);

        double targetVelocity1 = 0.0;
        double targetVelocity2 = 0.0;
        boolean motorsOn = false;
        boolean autoSpeed = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            limelight.update();

            if (gamepad1.rightBumperWasPressed())
                autoSpeed = !autoSpeed;

            // Any manual speed change hands control back to the driver
            if (gamepad1.dpadUpWasPressed()) {
                autoSpeed = false;
                targetVelocity1 += VELOCITY_STEP;
            }
            if (gamepad1.dpadDownWasPressed()) {
                autoSpeed = false;
                targetVelocity1 -= VELOCITY_STEP;
            }
            if (gamepad1.dpadRightWasPressed()) {
                autoSpeed = false;
                targetVelocity2 += VELOCITY_STEP;
            }
            if (gamepad1.dpadLeftWasPressed()) {
                autoSpeed = false;
                targetVelocity2 -= VELOCITY_STEP;
            }
            if (gamepad1.aWasPressed())
                motorsOn = !motorsOn;
            if (gamepad1.bWasPressed()) {
                autoSpeed = false;
                targetVelocity1 = 0.0;
                targetVelocity2 = 0.0;
            }
            // Stop before reversing so a wheel never flips direction at full speed
            if (gamepad1.xWasPressed()) {
                motorsOn = false;
                throwerMotor1.setDirection(flip(throwerMotor1.getDirection()));
            }
            if (gamepad1.yWasPressed()) {
                motorsOn = false;
                throwerMotor2.setDirection(flip(throwerMotor2.getDirection()));
            }

            // When the goal tag drops out of view, keep the last speeds
            if (autoSpeed && limelight.hasTarget()) {
                targetVelocity1 = interpolate(limelight.getDistance(), DISTANCES_IN, MOTOR1_SPEEDS);
                targetVelocity2 = interpolate(limelight.getDistance(), DISTANCES_IN, MOTOR2_SPEEDS);
            }

            targetVelocity1 = Math.max(0.0, Math.min(MAX_VELOCITY, targetVelocity1));
            targetVelocity2 = Math.max(0.0, Math.min(MAX_VELOCITY, targetVelocity2));

            throwerMotor1.setVelocity(motorsOn ? targetVelocity1 : 0.0);
            throwerMotor2.setVelocity(motorsOn ? targetVelocity2 : 0.0);

            turretMotor.setPower(gamepad1.right_stick_x * TURRET_MAX_POWER);

            telemetry.addData("Motors", motorsOn ? "ON  (A to stop)" : "OFF (A to start)");
            telemetry.addData("Speed mode", autoSpeed ? "AUTO from distance  (RB for manual)" : "MANUAL  (RB for auto)");
            limelight.addTelemetry(telemetry);
            telemetry.addLine();
            telemetry.addData("Motor 1 target", "%.0f ticks/s  (dpad up/down)", targetVelocity1);
            telemetry.addData("Motor 1 actual", "%.0f ticks/s", throwerMotor1.getVelocity());
            telemetry.addData("Motor 1 power", "%.2f", throwerMotor1.getPower());
            telemetry.addData("Motor 1 direction", "%s  (X to reverse)", throwerMotor1.getDirection());
            telemetry.addLine();
            telemetry.addData("Motor 2 target", "%.0f ticks/s  (dpad left/right)", targetVelocity2);
            telemetry.addData("Motor 2 actual", "%.0f ticks/s", throwerMotor2.getVelocity());
            telemetry.addData("Motor 2 power", "%.2f", throwerMotor2.getPower());
            telemetry.addData("Motor 2 direction", "%s  (Y to reverse)", throwerMotor2.getDirection());
            telemetry.addLine();
            telemetry.addData("Step", "%.0f ticks/s   B = reset targets", VELOCITY_STEP);
            telemetry.addLine();
            telemetry.addData("Turret position", "%d ticks  (right stick)", turretMotor.getCurrentPosition());
            telemetry.addData("Turret power", "%.2f", turretMotor.getPower());
            telemetry.update();
        }

        limelight.stop();
    }

    // Linear interpolation of ys at x; clamps to the first / last entry outside the table
    private static double interpolate(double x, double[] xs, double[] ys) {
        if (x <= xs[0])
            return ys[0];
        for (int i = 1; i < xs.length; i++) {
            if (x <= xs[i]) {
                double t = (x - xs[i - 1]) / (xs[i] - xs[i - 1]);
                return ys[i - 1] + t * (ys[i] - ys[i - 1]);
            }
        }
        return ys[ys.length - 1];
    }

    private static DcMotor.Direction flip(DcMotor.Direction direction) {
        return direction == DcMotor.Direction.FORWARD
                ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD;
    }
}
