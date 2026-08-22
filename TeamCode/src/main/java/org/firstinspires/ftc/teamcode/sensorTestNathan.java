package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.mechanisms.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class sensorTestNathan extends OpMode {

    private sensors sensors;

    @Override
    public void init(){
        sensors = new sensors(hardwareMap);
    }

    @Override
    public void loop(){
        int numberOfPresses = sensors.TouchCounter();

        telemetry.addData("Touch Sensor Count", numberOfPresses);
        telemetry.update();
    }
}

