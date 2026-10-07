package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "felixFlywheelTest")
public class felixFlywheelTest extends OpMode {

    private DcMotor flywheel;

    private double flywheelPower = 0.50;

    private boolean previousDpadUp = false;
    private boolean previousDpadDown = false;

    @Override
    public void init() {

        flywheel = hardwareMap.get(DcMotor.class, "testMotor");

        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Felix Flywheel Test");
        telemetry.addData("Power", flywheelPower);
        telemetry.update();
    }

    @Override
    public void loop() {

        // D-PAD UP: increase power
        if (gamepad1.dpad_up && !previousDpadUp) {
            flywheelPower += 0.05;

            if (flywheelPower > 1.0) {
                flywheelPower = 1.0;
            }
        }

        // D-PAD DOWN: decrease power
        if (gamepad1.dpad_down && !previousDpadDown) {
            flywheelPower -= 0.05;

            if (flywheelPower < 0.0) {
                flywheelPower = 0.0;
            }
        }

        previousDpadUp = gamepad1.dpad_up;
        previousDpadDown = gamepad1.dpad_down;

        // LEFT BUMPER: fire flywheel
        if (gamepad1.left_bumper) {
            flywheel.setPower(flywheelPower);
        } else {
            flywheel.setPower(0);
        }

        telemetry.addData("Flywheel Power", "%.2f", flywheelPower);
        telemetry.addData("Firing", gamepad1.left_bumper);
        telemetry.update();
    }
}