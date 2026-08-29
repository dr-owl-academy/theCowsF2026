package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Aaron Sensor Test", group = "Test")
public class aaron_sensor_test extends OpMode {

    private TouchSensor touchSensor;
    private DcMotor testMotor;

    private int pressCount = 0;
    private boolean wasPressed = false;

    @Override
    public void init() {
        // Look closely here: TouchSensor MUST be mapped to "testsensor"
        touchSensor = hardwareMap.get(TouchSensor.class, "testsensor");

        // DcMotor MUST be mapped to "testmotor"
        testMotor = hardwareMap.get(DcMotor.class, "testmotor");
    }

    @Override
    public void loop() {
        // Motor control logic via gamepad 1
        if (gamepad1.left_bumper) {
            testMotor.setPower(1.0);
        } else if (gamepad1.right_bumper) {
            testMotor.setPower(-1.0);
        } else {
            testMotor.setPower(0.0); // Stops the motor when neither is pressed
        }

        // Sensor and counting logic
        boolean isPressedNow = isPressed();
        double motorPower = testMotor.getPower();

        // Check for press transition (rising edge)
        if (isPressedNow && !wasPressed) {
            if (motorPower > 0) {
                pressCount++;
            } else if (motorPower < 0) {
                pressCount--;
            }
        }

        wasPressed = isPressedNow;

        // Standard telemetry
        telemetry.addData("Motor Power", motorPower);
        telemetry.addData("Touch Sensor Pressed", isPressedNow);
        telemetry.addData("Press Count", pressCount);
        telemetry.update();
    }

    public boolean isPressed() {
        return touchSensor != null && touchSensor.isPressed();
    }
}