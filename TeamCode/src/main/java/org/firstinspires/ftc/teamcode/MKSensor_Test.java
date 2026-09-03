package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.sensorMK;


@TeleOp(name = "MKSensor_Test")
public class MKSensor_Test extends OpMode {

    private sensorMK sensors;

    @Override
    public void init() {

        sensors = new sensorMK(hardwareMap);
    }
    @Override
    public void loop() {

        int numberOfPresses = sensors.countTouchSensorPresses();

        telemetry.addData("Touch Sensor Press Count", numberOfPresses);
        telemetry.update();
    }

}
