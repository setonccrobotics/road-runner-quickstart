package org.firstinspires.ftc.teamcode.SCC;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous(name="ElDuderino", group="SCC")
public class ElDuderino extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    // Constants
    static final double ARM_HOME_POS = 0.0;
    static final double ARM_HOOK_PICKUP_POS = 0.36;
    static final double ARM_HOOK_DROP_POS = 0.35;
    static final double ARM_ARCH_PICKUP_POS = 0.29;
    static final double ARM_ARCH_DROP_POS = 0.30;
    static final double ARM_CRADLE_POS = 0.265;
    static final double ARM_CRADLE_PICKUP_POS = 0.26;
    static final double ARM_PEDESTAL_POS = 0.155;
    static final double ARM_PEDESTAL_PICKUP_POS = 0.145;
    static final double PITCH_HOME_POS = 0.0;
    static final double PITCH_PEDISTAL_PICKUP_POS = 0.46;
    static final double PITCH_PEDISTAL_MOVE_POS = 0.41;
    static final double PITCH_PEDISTAL_DROP_POS = 0.42;
    static final double PITCH_CRADLE_PICKUP_POS = 0.5;
    static final double PITCH_CRADLE_LIFT_POS = 0.4;
    static final double PITCH_CRADLE_DROP_POS = 0.45;
    static final double PITCH_ARCH_PICKUP_AND_DROP_POS = 0.49;
    static final double PITCH_ARCH_LIFT_POS = 0.45;
    static final double PITCH_HOOK_PICKUP_POS = 0.5;
    static final double PITCH_HOOK_LIFT__POS = 0.47;
    static final double PITCH_HOOK_DROP_POS = 0.48;
    static final double WRIST_ROTATION_HOME_POS = 0.5;
    static final double LEFT_CLAW_HOME = 0.5;
    static final double RIGHT_CLAW_HOME = 0.5;
    static final double LEFT_CLAW_OPEN = 0.3;
    static final double RIGHT_CLAW_OPEN = 0.7;
    static final double LEFT_CLAW_HOOK_CLOSE = 0.58;
    static final double RIGHT_CLAW_HOOK_CLOSE = 0.42;
    private Servo positionServo;
    private Servo pitchServo;
    private Servo rotationServo;
    private Servo leftClawServo;
    private Servo rightClawServo;
    private DcMotor slideMotor = null;
    private TouchSensor slideHomeSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        positionServo = hardwareMap.get(Servo.class, "positionServo");
        pitchServo = hardwareMap.get(Servo.class, "pitchServo");
  /*      rotationServo = hardwareMap.get(Servo.class, "rotationServo");
        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        slideMotor = hardwareMap.get(DcMotor.class,"slideMotor");
        slideHomeSensor = hardwareMap.get(TouchSensor.class, "slideHomeSensor");

        // Configure the motors
        slideMotor.setDirection(DcMotor.Direction.REVERSE);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
*/
        // Wait for the DS start button to be touched.
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();

        //Swing test

        pitchServo.setPosition(PITCH_HOME_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOME_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_PEDESTAL_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_PEDISTAL_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_PEDISTAL_MOVE_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_PEDESTAL_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_PEDISTAL_DROP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_CRADLE_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_CRADLE_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_ARCH_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_ARCH_DROP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOOK_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOOK_DROP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOME_POS);

        if (isStopRequested()) return;
        sleep(3000);
        if (isStopRequested()) return;
/*
        // Zero the slide
        zero();

        // Go to the home position
        rotationServo.setPosition(WRIST_ROTATION_HOME_POS);
        sleep(500);
        pitchServo.setPosition(PITCH_HOME_POS);
        sleep(500);
        leftClawServo.setPosition(LEFT_CLAW_HOME);
        rightClawServo.setPosition(RIGHT_CLAW_HOME);
        sleep(500);
        positionServo.setPosition(ARM_HOME_POS);

        // Wait for the user to press the play button
        waitForStart();

        // Turn to the hook position
        positionServo.setPosition(ARM_HOOK_POS);
        sleep(500);

        // Prepare the claw to pick up the claw
        pitchServo.setPosition(PITCH_STRAIGHT_POS);
        leftClawServo.setPosition(LEFT_CLAW_OPEN);
        rightClawServo.setPosition(RIGHT_CLAW_OPEN);
        sleep(500);

        // Slide to the hook position
        slideToEncoderPos(2000);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        // Close the claw
        leftClawServo.setPosition(LEFT_CLAW_HOOK_CLOSE);
        rightClawServo.setPosition(RIGHT_CLAW_HOOK_CLOSE);
        sleep(500);

        pitchServo.setPosition(PITCH_LIFT_POS);
        sleep(500);

        // Slide to the hook position
        slideToEncoderPos(4000);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        leftClawServo.setPosition(LEFT_CLAW_OPEN);
        rightClawServo.setPosition(RIGHT_CLAW_OPEN);
        sleep(2000);


        // Go to the arch position
        positionServo.setPosition(ARM_ARCH_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;
/*
        // Go to the cradle position
        positionServo.setPosition(ARM_CRADLE_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_PEDESTAL_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

 */
    }
/*
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
    }

    public void slideToEncoderPos(int encoderPos) {
        slideMotor.setTargetPosition(encoderPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(1.0);
    }

    public void slideToEncoderPosBlocking(int encoderPos) {
        double percent = 10.0;
        double threshold = encoderPos * (percent / 100.0);
        double lowerBound = encoderPos - threshold;
        double upperBound = encoderPos + threshold;

        slideMotor.setTargetPosition(encoderPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(1.0);
        while (!(slideMotor.getCurrentPosition() >= lowerBound && slideMotor.getCurrentPosition() <= upperBound)) {
            // no op
        }
    }*/
}
