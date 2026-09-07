package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class sensors {

    private TouchSensor touchsensor;
    private ColorSensor colorsensor;
    private DcMotor testMotor;


    private int touchCount = 0;
    private boolean wasPressed = false;

    public sensors(HardwareMap hardwareMap) {
        touchsensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        testMotor = hardwareMap.get(DcMotor.class,"testMotor");
    }


    public void TouchCounter() {

        boolean isPressed = touchsensor.isPressed();
        double power = testMotor.getPower();

        if (isPressed && !wasPressed) {
            if (power == 1){
                touchCount++;
            }
            if (power == -1){
                touchCount --;
            }

        }

        wasPressed = isPressed;

    }
    public int returntouchcount(){
        return touchCount;
    }








}