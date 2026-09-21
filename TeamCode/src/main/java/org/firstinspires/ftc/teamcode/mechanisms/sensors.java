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
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }
/*

    public void ballCounter() {

        boolean isPressed = touchsensor.isPressed();

        // Pollen color range


        double power = testMotor.getPower();


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
 */


    public void senseColor() {

        amountOfRed = colorsensor.red();
        amountOfGreen = colorsensor.green();
        amountOfBlue = colorsensor.blue();
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
}