package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="flywheeltest")
public class flywheelMotor extends OpMode {
    private DcMotorEx flywheelMotor;
    private double power = 0;
    private double maxVelocity = 1000; // Encoder ticks per second

    @Override
    public void init() {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheelMotor");
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

        flywheelMotor.setVelocity(power * maxVelocity);
    }
}