package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="flywheeltest")
public class flywheelMotor extends OpMode {
    private DcMotor flywheelMotor;
    private double power = 0;

    @Override
    public void init() {
        flywheelMotor = hardwareMap.get(DcMotor.class, "flywheelMotor");
        flywheelMotor.setDirection(DcMotor.Direction.REVERSE);
        flywheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelMotor.setPower(0);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            if (power >= 1) {
                power = 1;
            }
            else if (power < 1) {
                power = Math.min(1, power + 0.1);
            }
        }
        else if (gamepad1.dpad_down) {
            if (power <= 0) {
                power = 0;
            }
            else if (power > 0) {
                power = Math.max(0, power - 0.1);
            }
        }

        flywheelMotor.setPower(power);
    }
}