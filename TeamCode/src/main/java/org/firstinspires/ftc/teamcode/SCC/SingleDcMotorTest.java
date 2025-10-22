package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="SingleDcMotorTest", group="SCC")
public class SingleDcMotorTest extends LinearOpMode {
    private DcMotor driveMotor = null;

    @Override
    public void  runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        driveMotor = hardwareMap.get(DcMotor.class, "motor");

        driveMotor.setPower(0.0);

        waitForStart();

        while (opModeIsActive()) {
            driveMotor.setPower(gamepad1.left_stick_y);
        }
    }
}
