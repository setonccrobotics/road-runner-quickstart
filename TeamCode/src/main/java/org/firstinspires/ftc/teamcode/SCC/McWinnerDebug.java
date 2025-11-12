package org.firstinspires.ftc.teamcode.SCC;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="McWinnerDebug", group="SCC")
public class McWinnerDebug extends LinearOpMode {
    public DcMotorEx leftFront, leftBack, rightBack, rightFront;

    @Override
    public void  runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setPower(0.0);
        leftBack.setPower(0.0);
        rightBack.setPower(0.0);
        rightFront.setPower(0.0);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x)
                leftFront.setPower(1.0);
            else
                leftFront.setPower(0.0);

            if (gamepad1.a)
                leftBack.setPower(1.0);
            else
                leftBack.setPower(0.0);

            if (gamepad1.b)
                rightBack.setPower(1.0);
            else
                rightBack.setPower(0.0);

            if (gamepad1.y)
                rightFront.setPower(1.0);
            else
                rightFront.setPower(0.0);
        }
    }
}
