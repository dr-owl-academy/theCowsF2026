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
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }

    @Override
    public void loop() {
        boolean isPressedNow = touchSensor.isPressed();

        // If left bumper is pressed, spin 1
        if (gamepad1.left_bumper) {
            testMotor.setPower(1.0);

            // If touch sensor is pressed during this, count up
            if (isPressedNow && !wasPressed) {
                pressCount++;
            }
        }
        // If right bumper is pressed, spin -1
        else if (gamepad1.right_bumper) {
            testMotor.setPower(-1.0);

            // If touch sensor is pressed during this, count down
            if (isPressedNow && !wasPressed) {
                pressCount--;
            }
        }
        // Otherwise stop the motor
        else {
            testMotor.setPower(0.0);
        }

        wasPressed = isPressedNow; // Saves the state for the next loop

        telemetry.addData("Press Count", pressCount);
        telemetry.update();
    }
}