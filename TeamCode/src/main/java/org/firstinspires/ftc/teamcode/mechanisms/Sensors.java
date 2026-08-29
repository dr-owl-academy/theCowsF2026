package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "Touch Sensor Test", group = "Sensors")
public class Sensors extends LinearOpMode {

    // Declare hardware member
    private TouchSensor touchSensor;

    @Override
    public void runOpMode() {
        // Initialize hardware inside runOpMode()
        touchSensor = hardwareMap.get(TouchSensor.class, "sensortouch");

        // Wait for the driver to press PLAY
        waitForStart();

        // Loop while the OpMode is running
        while (opModeIsActive()) {
            if (touchSensor.isPressed()) {
                telemetry.addData("Touch Sensor", "Is Pressed");
            } else {
                telemetry.addData("Touch Sensor", "Is Not Pressed");
            }

            telemetry.update();
        }
    }
}