package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftAssembly {
    private int previousLiftPosition = 0;
    private int previousSlidePosition = 0;
    private DcMotor liftMotor = null;
    private DcMotor slideMotor = null;
    private TouchSensor slideHomeSensor;
    private TouchSensor liftTopSensor;
    private TouchSensor liftBottomSensor;
    private TouchSensor liftHomeSensor;
    private Servo liftStabilityServo;

    static final int MIN_SLIDE_HOME_ENCODER_POS = 30;
    static final int MAX_SLIDE_UPPER_ENCODER_POS = 2900;
    static final double LIFT_STABILITY_HOME_POS = 0.52;
    static final double LIFT_STABILITY_OPEN_POS = 0.18;

    public LiftAssembly(HardwareMap hardwareMap) {
        // Configure the hardware map
        liftMotor = hardwareMap.get(DcMotor.class,"liftMotor");
        slideMotor = hardwareMap.get(DcMotor.class,"slideMotor");
        slideHomeSensor = hardwareMap.get(TouchSensor.class, "slideHomeSensor");
        liftStabilityServo = hardwareMap.get(Servo.class, "liftStabilityServo");

        // Configure the motors
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // MAYBE IF WE HOOK UP THE ENCODER WE CAN GET HOLD POWER BY DOING THIS ->>> liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftTopSensor = hardwareMap.get(TouchSensor.class, "liftTopSensor");
        liftBottomSensor = hardwareMap.get(TouchSensor.class, "liftBottomSensor");
        liftHomeSensor = hardwareMap.get(TouchSensor.class, "liftHomeSensor");
    }

    public void run(Gamepad gamepad1, Gamepad gamepad2) {
        if (gamepad2.right_trigger > 0.09
                && slideMotor.getCurrentPosition() < MAX_SLIDE_UPPER_ENCODER_POS) {
            // Extend the slide
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideMotor.setPower(gamepad2.right_trigger * 1.0);
            previousSlidePosition = slideMotor.getCurrentPosition();
        } else if (gamepad2.left_trigger > 0.09
                && slideMotor.getCurrentPosition() > MIN_SLIDE_HOME_ENCODER_POS
                && !slideHomeSensor.isPressed()) {
            // Retract the slide
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideMotor.setPower(gamepad2.left_trigger * -1.0);
            previousSlidePosition = slideMotor.getCurrentPosition();
        } else {
            if (!slideHomeSensor.isPressed()) {
                slideMotor.setTargetPosition(previousSlidePosition);
                slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slideMotor.setPower(-0.3);
            } else {
                slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                slideMotor.setPower(0.0);
            }
        }

        if (gamepad2.right_stick_y < 0.09 && !liftTopSensor.isPressed()) {
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(gamepad2.right_stick_y * 0.5);
            previousLiftPosition = liftMotor.getCurrentPosition();
        }
        else if (gamepad2.right_stick_y > 0.09 && !liftBottomSensor.isPressed()) {
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(gamepad2
                    .right_stick_y * 0.5);
            previousLiftPosition = liftMotor.getCurrentPosition();
        }
        else {
            liftMotor.setTargetPosition(previousLiftPosition);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotor.setPower(0.3);

        }
        if (gamepad1.a) {
            liftStabilityServo.setPosition(LIFT_STABILITY_OPEN_POS);
        }
        if (gamepad1.b) {
            liftStabilityServo.setPosition(LIFT_STABILITY_HOME_POS);
        }
    }

    public void zero() {
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Zero the linear slide
        while (!slideHomeSensor.isPressed()) {
            slideMotor.setPower(-0.9);
        }

        // Back off the home switch
        while (slideHomeSensor.isPressed()) {
            slideMotor.setPower(0.3);
        }
        slideMotor.setPower(0.0);

        // Reset the encoder zero position
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        previousSlidePosition = 100;

        liftStabilityServo.setPosition(LIFT_STABILITY_HOME_POS);
    }

    public void zeroLiftOnly() {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Zero the lift
        while (liftHomeSensor.isPressed() || liftBottomSensor.isPressed()) {
            liftMotor.setPower(-0.2);
        }

        // Back off the home switch
        while (!liftHomeSensor.isPressed()) {
            liftMotor.setPower(0.2);
        }
        liftMotor.setPower(0.0);

        // Reset the encoder zero position
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("slideMotor Current Position", slideMotor.getCurrentPosition());
        telemetry.addData("liftMotor Current Position", liftMotor.getCurrentPosition());
        telemetry.addData("liftMotor Previous Position", previousLiftPosition);
    }
    public void liftToEncoderPos(int encoderPos) {
        liftMotor.setTargetPosition(encoderPos);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(0.9);
    }

    public void slideToEncoderPos(int encoderPos) {
        slideMotor.setTargetPosition(encoderPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(1.0);
    }

    public void liftToHomePos() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (!liftBottomSensor.isPressed()) {
            liftMotor.setPower(0.3);
        }
        liftMotor.setPower(0.0);
    }

    public int slideMotorCurrentPosition() {
        int encoderPos = slideMotor.getCurrentPosition();
        return encoderPos;
    }

    public void liftToTop() {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (!liftTopSensor.isPressed()) {
            liftMotor.setPower(-0.8);
        }
        liftMotor.setPower(0.0);
    }

    public void extendStabilityServo() {
        liftStabilityServo.setPosition(LIFT_STABILITY_OPEN_POS);
    }
}
