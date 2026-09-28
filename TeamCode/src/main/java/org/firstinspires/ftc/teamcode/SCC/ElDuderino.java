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
    static final double ARM_HOOK_FINAL_POS = 3.2;
    static final double ARM_HOOK_PICKUP_POS = 0.372;
    static final double ARM_HOOK_DROP_POS = 0.358;
    static final double ARM_ARCH_PICKUP_POS = 0.3;
    static final double ARM_ARCH_DROP_POS = 0.302;
    static final double ARM_ARCH_FINAL_PUSH_POS = 0.301;
    static final double ARM_CRADLE_POS = 0.267;
    static final double ARM_CRADLE_PICKUP_POS = 0.26;
    static final double ARM_CRADLE_EXTENSION_POS = 0.26;
    static final double ARM_PEDESTAL_POS = 0.155;
    static final double ARM_PEDESTAL_PICKUP_POS = 0.148;
    static final double PITCH_HOME_POS = 0.0;
    static final double PITCH_PEDISTAL_PICKUP_POS = 0.46;
    static final double PITCH_PEDISTAL_MOVE_POS = 0.41;
    static final double PITCH_PEDISTAL_DROP_POS = 0.42;
    static final double PITCH_PEDISTAL_LEAVE_POS = 0.45;
    static final double PITCH_CRADLE_PICKUP_POS = 0.5;
    static final double PITCH_CRADLE_LIFT_POS = 0.4;
    static final double PITCH_CRADLE_DROP_POS = 0.46;
    static final double PITCH_ARCH_PICKUP_AND_DROP_POS = 0.49;
    static final double PITCH_ARCH_LIFT_POS = 0.46;
    static final double PITCH_ARCH_PUSH_POS = 0.53;
    static final double PITCH_HOOK_PICKUP_POS = 0.5;
    static final double PITCH_HOOK_LIFT_POS = 0.492;
    static final double PITCH_HOOK_DROP_POS = 0.48;
    static final double WRIST_ROTATION_HOME_POS = 0.0;
    static final double WRIST_ROTATION_SIDE_POS = 0.3;
    static final double CLAW_HOME = 0.2;
    static final double CLAW_OPEN = 0.47;
    static final double CLAW_CLOSE = 0.24;
    private Servo positionServo;
    private Servo pitchServo;
    private Servo rotationServo;
    private Servo clawServo;
    private DcMotor slideMotor = null;
    private TouchSensor slideHomeSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        positionServo = hardwareMap.get(Servo.class, "positionServo");
        pitchServo = hardwareMap.get(Servo.class, "pitchServo");
        rotationServo = hardwareMap.get(Servo.class, "rotationServo");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        slideMotor = hardwareMap.get(DcMotor.class,"slideMotor");
        slideHomeSensor = hardwareMap.get(TouchSensor.class, "slideHomeSensor");

        // Configure the motors
        slideMotor.setDirection(DcMotor.Direction.REVERSE);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pitchServo.setPosition(PITCH_HOME_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        rotationServo.setPosition(WRIST_ROTATION_HOME_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        // Zero the slide
        zero();

        clawServo.setPosition(CLAW_HOME);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOME_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        // Wait for the DS start button to be touched.
        telemetry.addData(">", "Touch Play to run OpMode");
        telemetry.update();


        // Wait for the user to press the play button
        waitForStart();

        //Pedestal
        positionServo.setPosition(ARM_PEDESTAL_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_OPEN);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_PEDISTAL_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(250);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_CLOSE);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_PEDISTAL_MOVE_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_PEDESTAL_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        slideToEncoderPos(900);

        pitchServo.setPosition(PITCH_PEDISTAL_DROP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_OPEN);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_PEDISTAL_LEAVE_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

       /* positionServo.setPosition(ARM_PEDESTAL_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;
       */
        slideToEncoderPos(200);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        //Cradle
        positionServo.setPosition(ARM_CRADLE_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_CRADLE_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        rotationServo.setPosition(WRIST_ROTATION_SIDE_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(1520);

        if (isStopRequested()) return;
        sleep(1500);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_CLOSE);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_CRADLE_LIFT_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        rotationServo.setPosition(WRIST_ROTATION_HOME_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_CRADLE_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        slideToEncoderPos(1900);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_CRADLE_DROP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_OPEN);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_CRADLE_LIFT_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        //Arch
        slideToEncoderPos(400);

        if (isStopRequested()) return;
        sleep(1500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_ARCH_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        rotationServo.setPosition(WRIST_ROTATION_HOME_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_ARCH_PICKUP_AND_DROP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(750);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_CLOSE);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_ARCH_DROP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_ARCH_LIFT_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(950);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_ARCH_PICKUP_AND_DROP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_OPEN);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(800);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_CLOSE);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_ARCH_PUSH_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(1300,0.2);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        slideToEncoderPos(50);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        //Hook
        pitchServo.setPosition(PITCH_HOOK_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        rotationServo.setPosition(WRIST_ROTATION_SIDE_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOOK_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_OPEN);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(500,0.2);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_CLOSE);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_HOOK_LIFT_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOOK_DROP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        slideToEncoderPos(1500,0.2);

        if (isStopRequested()) return;
        sleep(2000);
        if (isStopRequested()) return;

        pitchServo.setPosition(PITCH_CRADLE_PICKUP_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        clawServo.setPosition(CLAW_OPEN);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOOK_DROP_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        slideToEncoderPos(200);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        //Return
        pitchServo.setPosition(PITCH_HOME_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        rotationServo.setPosition(WRIST_ROTATION_HOME_POS);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        // Zero the slide
        zero();

        clawServo.setPosition(CLAW_HOME);

        if (isStopRequested()) return;
        sleep(500);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_CRADLE_POS);

        if (isStopRequested()) return;
        sleep(1000);
        if (isStopRequested()) return;

        positionServo.setPosition(ARM_HOME_POS);

        if (isStopRequested()) return;
        sleep(2000);
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

    public void zero() {
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Zero the linear slide
        while (!slideHomeSensor.isPressed()) {
            slideMotor.setPower(-0.2);
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
        slideMotor.setPower(0.7);//0.2);
    }

    public void slideToEncoderPos(int encoderPos, double power) {
        slideMotor.setTargetPosition(encoderPos);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(power);
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
    }
}
