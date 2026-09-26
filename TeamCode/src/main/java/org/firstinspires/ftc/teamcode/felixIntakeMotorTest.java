package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="felixIntakeMotorTest")
public class felixIntakeMotorTest extends OpMode {

    private DcMotor intake;

    // Starting power
    private double power = 0.5;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intake");
    }

    @Override
    public void loop() {

        // Increase power
        if (gamepad1.dpad_up) {
            power += 0.1;
        }

        // Decrease power
        if (gamepad1.dpad_down) {
            power -= 0.1;
        }

        // Keep power between 0 and 1
        if (power > 1.0) {
            power = 1.0;
        }

        if (power < 0.0) {
            power = 0.0;
        }

        // B = forward
        if (gamepad1.b) {
            intake.setPower(power);
        }

        // A = reverse
        else if (gamepad1.a) {
            intake.setPower(-power);
        }

        // Nothing pressed = stop
        else {
            intake.setPower(0);
        }

        telemetry.addData("Power", "%.1f", power);
        telemetry.addData("Motor Power", "%.1f", intake.getPower());
        telemetry.update();
    }
}

