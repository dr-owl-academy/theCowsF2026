package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

@TeleOp(name = "coach_sensor_test")
public class coach_sensor_test extends OpMode {

    private sensors sensors;

    @Override
    public void init() {

        sensors = new sensors(hardwareMap);
    }

    @Override
    public void loop() {

        int numberOfPresses = sensors.countTouchSensorPresses();

        telemetry.addData("Touch Sensor Press Count", numberOfPresses);
        telemetry.update();
    }
}