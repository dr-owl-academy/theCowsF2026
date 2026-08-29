package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

@TeleOp(name = "adrian sensor test")
public class sensortest extends OpMode {

    private sensors sensors;

    @Override
    public void init() {
        sensors = new sensors(hardwareMap);
    }

    @Override
    public void loop() {

        int numberOfPresses = sensors.countpresses();

        telemetry.addData("press counter:", numberOfPresses);
        telemetry.update();
    }
}