package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

@TeleOp(name="felix_Sensor_Test")
public class felix_Sensor_Test extends OpMode {

    private sensors sensors;
    private DcMotor testMotor;
    private ColorSensor colorSensor;

    @Override
    public void init() {
        sensors = new sensors(hardwareMap);
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");
    }

    @Override
    public void loop() {

        // Update sensor readings
        sensors.ballCounter();
        sensors.senseColor();

        // Motor controls
        if (gamepad1.b) {
            testMotor.setPower(1);
        }
        else if (gamepad1.a) {
            testMotor.setPower(-1);
        }
        else {
            testMotor.setPower(0);
        }

        // Color sensor values
        telemetry.addData("Red", sensors.returnred());
        telemetry.addData("Green", sensors.returngreen());
        telemetry.addData("Blue", sensors.returnblue());

        // Ball detection
        telemetry.addData("Pollen", sensors.returnPollen());
        telemetry.addData("Red Nectar", sensors.returRedNectar());
        telemetry.addData("Blue Nectar", sensors.returBlueNectar());

        // Other values
        telemetry.addData("Touch Count", sensors.returntouchcount());
        telemetry.addData("Motor Power", testMotor.getPower());

        telemetry.update();
    }
}