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
    private int amountOfRed = 0;
    private int amountOfBlue = 0;
    private int amountOfGreen = 0;

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
    public void colors(){
        amountOfRed = colorsensor.red();
        amountOfGreen = colorsensor.red();
        amountOfBlue = colorsensor.blue();



    }
    public int returnred(){
        return amountOfRed;
    }
    public int returnblue(){
        return amountOfBlue;
    }
    public int returngreen(){
        return amountOfGreen;
    }








}