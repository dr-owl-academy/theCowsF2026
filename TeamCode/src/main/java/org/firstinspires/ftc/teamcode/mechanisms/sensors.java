package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class sensors {

    private TouchSensor touchSensor;
    private ColorSensor colorSensor;


    private int touchCount = 0;
    private int amountOfBlue = 0;
    private int amountOfGreen = 0;
    private int amountOfRed = 0;
    private boolean wasPressed = false;

    public sensors(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");
    }


    public int TouchCounter() {

        boolean isPressed = touchSensor.isPressed();

        if (isPressed && !wasPressed) {
            touchCount++;
        }

        wasPressed = isPressed;
        return touchCount;
    }
    public int ColorSensorBlue(){
        amountOfBlue = colorSensor.blue();

        // returning all the variables from the color sensor
        return amountOfBlue;



    }
    public int ColorSensorGreen(){
        amountOfGreen = colorSensor.green();

        // returning all the variables from the color sensor
        return amountOfGreen;



    }
    public int ColorSensorRed(){
        amountOfRed = colorSensor.red();

        // returning all the variables from the color sensor
        return amountOfRed;



    }

}