package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;
@TeleOp(name = "Adrian sensor test")
public class sensortest {
    private sensors sensors;
    @Override
    public void init(){
        sensors = new sensors(hardwareMap);
    }
    @Override
    public void loop() {
        int presses = sensors.countpresses();

        telemetry.addData("Touch Sensor Press Count", presses);
        telemetry.update();

    }

}
