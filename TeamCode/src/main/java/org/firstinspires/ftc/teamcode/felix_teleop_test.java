package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

@TeleOp(name = "Felix Teleop Test", group = "Test")
public class felix_teleop_test extends OpMode {

    private DcMotor testmotor;
    private sensors sensors;

    @Override
    public void init() {

        // Get the intake motor
        testmotor = hardwareMap.get(DcMotor.class, "testmotor");

        // Create the sensor mechanism
        sensors = new sensors(hardwareMap);

        // Start motor stopped
        testmotor.setPower(0);
    }

    @Override
    public void loop() {

        // Update the touch sensor
        sensors.update();

        // Intake control
        if (gamepad1.left_bumper) {
            // Intake forwards
            testmotor.setPower(1.0);
        }
        else if (gamepad1.right_bumper) {
            // Intake backwards
            testmotor.setPower(-1.0);
        }
        else {
            // No bumper pressed
            testmotor.setPower(0);
        }

        // Get touch count
        int numberOfPresses = sensors.TouchCounter();

        // Telemetry
        telemetry.addData("Touch Count", numberOfPresses);
        telemetry.update();
    }
}