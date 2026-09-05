package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class sensors {
    private final TouchSensor touchSensor;
    private final ColorSensor colorSensor;
    private double power;
    private final DcMotor testMotor;
    private boolean wasPressed = false;
    private int Counter = 0;
    public sensors(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");
    }

    public int TouchCounter() {

    // send the info back to driver station using telemetry function.
        if(touchSensor.isPressed() && !wasPressed) {
            power = testMotor.getPower();
            if(power > 0.5){
                Counter++;
            }
            if(power < -0.5) {
                Counter--;
            }
        }
        wasPressed = touchSensor.isPressed();
    return Counter;
    }
    public void colorDetector(){
        if (colorSensor.green() > 10500 && colorSensor.red() > 6500 && colorSensor.blue() > 2500 && colorSensor.green() < 13000 && colorSensor.red() < 8000 && colorSensor.blue() < 4000) {
            testMotor.setPower(1);
        }
        else {
            testMotor.setPower(0);
        }
    }
}