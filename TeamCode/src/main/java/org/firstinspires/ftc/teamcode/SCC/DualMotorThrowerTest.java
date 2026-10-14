package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Dial in speeds for a two-motor thrower.
//   D-pad up/down    : motor 1 speed +/- one step
//   D-pad left/right : motor 2 speed -/+ one step
//   A                : start / stop both motors (targets are kept)
//   B                : reset both targets to zero
//   X / Y            : reverse motor 1 / motor 2 (also stops the motors)
@TeleOp(name="DualMotorThrowerTest", group="SCC")
public class DualMotorThrowerTest extends LinearOpMode {
    // Speeds are in encoder ticks per second, same units as RobotConveyor's launcher
    static final double VELOCITY_STEP = 50.0;
    static final double MAX_VELOCITY = 3000.0;

    private DcMotorEx throwerMotor1 = null;
    private DcMotorEx throwerMotor2 = null;

    @Override
    public void runOpMode() {
        throwerMotor1 = hardwareMap.get(DcMotorEx.class, "throwerMotor1");
        throwerMotor2 = hardwareMap.get(DcMotorEx.class, "throwerMotor2");

        // Starting directions; X / Y flip them while the OpMode runs
        throwerMotor1.setDirection(DcMotor.Direction.FORWARD);
        throwerMotor2.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotorEx motor : new DcMotorEx[] {throwerMotor1, throwerMotor2}) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setVelocity(0.0);
        }

        double targetVelocity1 = 0.0;
        double targetVelocity2 = 0.0;
        boolean motorsOn = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpadUpWasPressed())
                targetVelocity1 += VELOCITY_STEP;
            if (gamepad1.dpadDownWasPressed())
                targetVelocity1 -= VELOCITY_STEP;
            if (gamepad1.dpadRightWasPressed())
                targetVelocity2 += VELOCITY_STEP;
            if (gamepad1.dpadLeftWasPressed())
                targetVelocity2 -= VELOCITY_STEP;
            if (gamepad1.aWasPressed())
                motorsOn = !motorsOn;
            if (gamepad1.bWasPressed()) {
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

            targetVelocity1 = Math.max(0.0, Math.min(MAX_VELOCITY, targetVelocity1));
            targetVelocity2 = Math.max(0.0, Math.min(MAX_VELOCITY, targetVelocity2));

            throwerMotor1.setVelocity(motorsOn ? targetVelocity1 : 0.0);
            throwerMotor2.setVelocity(motorsOn ? targetVelocity2 : 0.0);

            telemetry.addData("Motors", motorsOn ? "ON  (A to stop)" : "OFF (A to start)");
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
            telemetry.update();
        }
    }

    private static DcMotor.Direction flip(DcMotor.Direction direction) {
        return direction == DcMotor.Direction.FORWARD
                ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD;
    }
}
