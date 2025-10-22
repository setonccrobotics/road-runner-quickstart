
package org.firstinspires.ftc.teamcode.SCC;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SimpleServoTest", group = "SCC")
public class SimpleServoTest extends LinearOpMode
{
    @Override
    public void runOpMode() {
        Servo servo;

        // Initialize the servo
        servo = hardwareMap.get(Servo.class, "servo"); // Use the name from your configuration

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Control the servo position using gamepad input (example)
            if (gamepad1.a) {
                servo.setPosition(1.0); // Fully extended
            } else if (gamepad1.b) {
                servo.setPosition(0.5); // Midway
            } else if (gamepad1.x) {
                servo.setPosition(0.0); // Fully retracted
            }

            telemetry.addData("Servo Position", servo.getPosition());
            telemetry.update();
        }
    }
}