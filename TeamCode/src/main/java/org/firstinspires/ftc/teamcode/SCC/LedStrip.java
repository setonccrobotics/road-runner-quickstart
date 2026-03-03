package org.firstinspires.ftc.teamcode.SCC;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class LedStrip {

    // Declare the LED strip as a Servo
    private Servo ledStrip;
    private double currentLedStrip = 0.35;
    private ElapsedTime buttonTimer = new ElapsedTime();

    public LedStrip(HardwareMap hardwareMap){
        ledStrip = hardwareMap.get(Servo.class, "ledStrip");
    }

    public void run(Gamepad gamepad){

    }

    public void setLedColor(double color){
        ledStrip.setPosition(color);
    }

    /*public void runOpMode() {
        // Initialize the servo/LED on a port (e.g., Servo Port 0)
        ledStrip = hardwareMap.get(Servo.class, "ledStrip");

        // Wait for the start button
        telemetry.addData(">", "Press Up or Down on D-Pad" );
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (buttonTimer.milliseconds() > 1000) {
                // Update current LED strip value based on user input
                if (gamepad1.dpad_up && currentLedStrip < 1.0) {
                    currentLedStrip += 0.01;
                } else if (gamepad1.dpad_down && currentLedStrip > 0.0) {
                    currentLedStrip -= 0.01;
                }
                buttonTimer.reset();
            }

            // Set position to a value between 0.0 and 1.0
            // The goBILDA Prism driver maps 0-1 to different colors/patterns
            ledStrip.setPosition(currentLedStrip);

            telemetry.addData("LED Position", ledStrip.getPosition());
            telemetry.update();
        }
    }*/
}