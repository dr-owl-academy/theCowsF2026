package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="flywheeltest")
public class flywheelMotor extends OpMode {
    private DcMotor flywheelMotor;
    private double power = 0;

    private boolean previousUp = false;
    private boolean previousDown = false;

    @Override
    public void init() {
        flywheelMotor = hardwareMap.get(DcMotor.class, "testMotor");;
        "]"
        flywheelMotor.setDirection(DcMotor.Direction.REVERSE);
        flywheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelMotor.setPower(0);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up && !previousUp) {
            power = Math.min(1, power + 0.1);
        }
        else if (gamepad1.dpad_down && !previousDown) {
            power = Math.max(0, power - 0.1);
        }

        previousUp = gamepad1.dpad_up;
        previousDown = gamepad1.dpad_down;

        flywheelMotor.setPower(power);

        telemetry.addData("Power", "%.1f", power);
        telemetry.update();
    }

    @Override
    public void stop() {
        flywheelMotor.setPower(0);
    }
}