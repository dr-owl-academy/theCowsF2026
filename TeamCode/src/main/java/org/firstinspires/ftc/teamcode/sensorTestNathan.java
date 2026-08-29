package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.mechanisms.sensors;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class sensorTestNathan extends OpMode {

    private sensors sensors;
    private DcMotor testMotor;

    @Override
    public void init(){
        sensors = new sensors(hardwareMap);
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }

    @Override
    public void loop(){
        int numberOfPresses = sensors.TouchCounter();
        if (gamepad1.aWasPressed()){
            testMotor.setPower(1);
        }
        if (gamepad1.bWasPressed()){
            testMotor.setPower(-1);
        }
        if (gamepad1.xWasPressed()){
            testMotor.setPower(0);
        }
        telemetry.addData("Touch Sensor Count", numberOfPresses);
        telemetry.update();
    }
}

