package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "felix_Teleop_Test", group = "Test")
public class felix_Teleop_Test extends OpMode {

    private DcMotor testmotor;
    private TouchSensor touchsensor;

    private int touchCount = 0;
    private boolean wasPressed = false;

    @Override
    public void init() {

        testmotor = hardwareMap.get(DcMotor.class, "testmotor");
        touchsensor = hardwareMap.get(TouchSensor.class, "touchsensor");

        testmotor.setPower(0);

        telemetry.addLine("felix_Teleop_Test Ready");
        telemetry.update();
    }

    @Override
    public void loop() {

        // Intake control
        if (gamepad1.left_bumper) {
            testmotor.setPower(1.0);
        } else if (gamepad1.right_bumper) {
            testmotor.setPower(-1.0);
        } else {
            testmotor.setPower(0);
        }

        // Touch sensor
        boolean isPressed = touchsensor.isPressed();

        // Detect only a NEW press
        boolean newPress = isPressed && !wasPressed;

        if (newPress) {

            if (gamepad1.right_bumper) {
                touchCount--;
            } else {
                touchCount++;
            }
        }

        wasPressed = isPressed;

        // Telemetry
        telemetry.addData("Touch Sensor",
                isPressed ? "TOUCHED" : "NOT TOUCHED");

        telemetry.addData("Touch Count", touchCount);
        telemetry.addData("Intake Power", testmotor.getPower());

        telemetry.update();
    }
}