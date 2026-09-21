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
    private int pollenCount = 0;
    private int redNectarCount = 0;
    private int blueNectarCount = 0;
    private boolean pollenSensed = false;
    private boolean redNectarSensed = false;
    private boolean blueNectarSensed = false;
    double power = testMotor.getPower();



    public sensors(HardwareMap hardwareMap) {

        touchsensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }


    public void ballCounter() {

        boolean isPressed = touchsensor.isPressed();

        // Pollen color range





        // Remember that Pollen was seen


        // Only count if:
        // 1. Pollen was seen FIRST
        // 2. The touch sensor is newly pressed
        if (isPressed && !wasPressed) {

            // Intake
            if (power > 0.5) {
                touchCount++;
            }

            // Outtake
            else if (power < -0.5) {
                touchCount--;
            }

            // Reset so the next Pollen can be counted

        }


        // Remember the button state
        wasPressed = isPressed;
    }


    public int returntouchcount() {

        return touchCount;
    }



    public boolean senseColor() {

        amountOfRed = colorsensor.red();
        amountOfGreen = colorsensor.green();
        amountOfBlue = colorsensor.blue();
        boolean pollenSensed = amountOfBlue >0  && amountOfGreen >0  && amountOfRed >0  && amountOfBlue <2  && amountOfGreen <3  && amountOfRed <2 ;
        boolean redNectarSensed = amountOfBlue >0  && amountOfGreen >0  && amountOfRed >0  && amountOfBlue <2  && amountOfGreen <3  && amountOfRed <2 ;
        boolean blueNectarSensed = amountOfBlue >0  && amountOfGreen >0  && amountOfRed >0  && amountOfBlue <2  && amountOfGreen <3  && amountOfRed <2 ;
       return pollenSensed;


    }





    public int returnblue() {

        return amountOfBlue;
    }


    public int returngreen() {

        return amountOfGreen;
    }
    public int returnred() {

        return amountOfRed  ;
    }
    public boolean returRedNectar(){
        return redNectarSensed ;

    }
    public boolean returBlueNectar(){
        return blueNectarSensed ;

    }

}