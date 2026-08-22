package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

@TeleOp(name="felix_Sensor_Test")
public class felix_Sensor_Test extends OpMode {

    private sensors sensors;

    @Override
    public void init() {
        sensors =new sensors(hardwareMap);

    }

    @Override
    public void loop() {

        sensors.update();

        int numberOfPresses = sensors.TouchCounter();

        telemetry.addData("Touch Count", numberOfPresses);
        telemetry.update();
    }


}
