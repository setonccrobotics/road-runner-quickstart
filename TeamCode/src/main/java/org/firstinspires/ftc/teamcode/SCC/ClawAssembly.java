package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ClawAssembly {

    static final double WRIST_ROTATION_MIN = 0.0;
    static final double WRIST_ROTATION_MAX = 1.0;
    static final double WRIST_ROTATION_HOME_POS = 0.5;
    //static final double WRIST_PITCH_MIN = 0.28;
    static final double WRIST_PITCH_MIN = 0.55;
    static final double WRIST_PITCH_MAX_WITH_EXTENSION = 0.99;
    //static final double WRIST_PITCH_MAX = 0.73;
    static final double WRIST_PITCH_MAX = 0.83;
    static final double WRIST_PITCH_HOME_POS = 0.78;
    static final double WRIST_ROTATION_INCREMENT = 0.02;
    static final double WRIST_PITCH_INCREMENT = 0.02;
    //private CRServo leftClawServo;
    //private CRServo rightClawServo;
    private Servo leftClawServo;
    private Servo rightClawServo;
    private Servo wristRotationServo;
    // front servo rotates on z-axis
    private Servo wristPitchServo;

    private double currentWristServoPos = WRIST_ROTATION_HOME_POS;
    private double currentPitchServoPos = WRIST_PITCH_HOME_POS;

    // back Servo rotates on y-axis

    private ElapsedTime wristRotationTimer = new ElapsedTime();
    private ElapsedTime wristPitchTimer = new ElapsedTime();
    static final int WRIST_ROTATION_MOVEMENT_DELAY_MS = 50;
    static final int WRIST_PITCH_MOVEMENT_DELAY_MS = 80;


    public ClawAssembly(HardwareMap hardwareMap) {
        //leftClawServo = hardwareMap.get(CRServo.class, "leftClawServo");
        //rightClawServo = hardwareMap.get(CRServo.class, "rightClawServo");
        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        //rightClawServo.setDirection(DcMotorSimple.Direction.REVERSE);
        wristRotationServo = hardwareMap.get(Servo.class, "wristRotationServo");
        wristPitchServo = hardwareMap.get(Servo.class, "wristPitchServo");
    }
    public void run(Gamepad gamepad, int currentSlideEncoderPos) {
        if (gamepad.a) {
            // Open the claws
            clawOpen();
        } else if (gamepad.b) {
            // Close the claws
            clawClose();
        }
        else if (gamepad.y){
            wristRotationServo.setPosition(WRIST_ROTATION_HOME_POS);
            wristPitchServo.setPosition(WRIST_PITCH_HOME_POS);
            currentWristServoPos = WRIST_ROTATION_HOME_POS;
            currentPitchServoPos = WRIST_PITCH_HOME_POS;
        }
        /*if (gamepad.a) {
            leftClawServo.setPower(1.0);
            rightClawServo.setPower(1.0);
        }
        else if (gamepad.b) {
            leftClawServo.setPower(-0.15);
            rightClawServo.setPower(-0.15);
        }
        else
        {
            leftClawServo.setPower(0.0);
            rightClawServo.setPower(0.0);
        }*/

        if (wristRotationTimer.milliseconds() < WRIST_ROTATION_MOVEMENT_DELAY_MS) {
            // no op
        }
        else if ((gamepad.left_stick_x < -0.02 || gamepad.dpad_left) && currentWristServoPos < WRIST_ROTATION_MAX) {
            currentWristServoPos += WRIST_ROTATION_INCREMENT;
            wristRotationServo.setPosition(currentWristServoPos);
            wristRotationTimer.reset();
        }
        else if ((gamepad.left_stick_x > 0.02 || gamepad.dpad_right) && currentWristServoPos > WRIST_ROTATION_MIN) {
            currentWristServoPos -= WRIST_ROTATION_INCREMENT;
            wristRotationServo.setPosition(currentWristServoPos);
            wristRotationTimer.reset();
        }

        if (currentPitchServoPos >= WRIST_PITCH_MAX && currentSlideEncoderPos < 140) {
            wristPitchServo.setPosition(WRIST_PITCH_MAX);
            currentPitchServoPos = WRIST_PITCH_MAX;
        }
        if (wristPitchTimer.milliseconds() < WRIST_PITCH_MOVEMENT_DELAY_MS) {
            // no op
        }
        else if ((gamepad.left_stick_y < -0.02 || gamepad.dpad_up) && currentPitchServoPos > WRIST_PITCH_MIN) {
            currentPitchServoPos -= WRIST_PITCH_INCREMENT;
            wristPitchServo.setPosition(currentPitchServoPos);
            wristPitchTimer.reset();
        }
        else if ((gamepad.left_stick_y > 0.02 || gamepad.dpad_down) && currentPitchServoPos < WRIST_PITCH_MAX) {
            currentPitchServoPos += WRIST_PITCH_INCREMENT;
            wristPitchServo.setPosition(currentPitchServoPos);
            wristPitchTimer.reset();
        }
        else if ((gamepad.left_stick_y > 0.02 || gamepad.dpad_down) && currentPitchServoPos < WRIST_PITCH_MAX_WITH_EXTENSION && currentSlideEncoderPos > 500) {
            currentPitchServoPos += WRIST_PITCH_INCREMENT;
            wristPitchServo.setPosition(currentPitchServoPos);
            wristPitchTimer.reset();
        }
    }

    public void addTelemetry(Telemetry telemetry) {
        //telemetry.addData("currentWristServoPos",  "%7d", currentWristServoPos);
        //telemetry.addData("wristRotationServo", "%7d", wristRotationServo.getPosition());
    }

    public void zero() {
        wristRotationServo.setPosition(WRIST_ROTATION_HOME_POS);
        wristPitchServo.setPosition(WRIST_PITCH_HOME_POS);
    }

    public void deliverSampleOn() {
        //leftClawServo.setPower(-0.15);
        //rightClawServo.setPower(-0.15);
    }

    public void deliverSampleOff() {
        //leftClawServo.setPower(0.0);
        //rightClawServo.setPower(0.0);
    }

    public void setWristPos(double wristPos) {
        wristPitchServo.setPosition(wristPos);
    }

    public void clawOpen() {
        // Open the claws
        leftClawServo.setPosition(0.55); // Smaller number is MORE open
        rightClawServo.setPosition(0.53); // Bigger number is MORE open
    }
    public void clawClose() {
        // Close the claws
        leftClawServo.setPosition(0.65); // Bigger number is MORE closed
        rightClawServo.setPosition(0.43); // Smaller number is MORE closed
    }

}