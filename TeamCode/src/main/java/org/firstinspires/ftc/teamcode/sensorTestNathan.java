package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class sensorTestNathan extends OpMode {

    private sensors sensors;
    private DcMotor testMotor;
    private ColorSensor colorSensor;

    @Override
    public void init(){
        sensors = new sensors(hardwareMap);
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        colorSensor = hardwareMap.get(ColorSensor.class,"colorsensor");
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

        // Yellow = lots of red AND lots of green
        if (colorSensor.green() > 0.5 && colorSensor.red() > 0.5) {
            testMotor.setPower(1);
        }
        else {
            testMotor.setPower(0);
        }
    }
}

