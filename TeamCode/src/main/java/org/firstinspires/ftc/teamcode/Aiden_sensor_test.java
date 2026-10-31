package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.Sensors;

@TeleOp(name = "Aiden_sensor_test")
public class Aiden_sensor_test extends OpMode {
    private Sensors sensors;

    @Override
    public void init(){
        sensors=new Sensors(hardwareMap);
    }

    @Override
    public void loop(){
        sensors.updateItemCount(true, false);
        int numberOfPresses = sensors.getItemCount();
        telemetry.addData("Touch Sensor Press Count", numberOfPresses);
        telemetry.update();
    }
}
