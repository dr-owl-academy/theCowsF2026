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

    // Remembers if Pollen was detected
    private boolean wasSeen = false;


    public sensors(HardwareMap hardwareMap) {

        touchsensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }


    public void ballCounter() {

        boolean isPressed = touchsensor.isPressed();

        // Pollen color range
        boolean isSeen =
                colorsensor.green() > 9500 &&
                        colorsensor.red() > 6000 &&
                        colorsensor.blue() > 2000 &&
                        colorsensor.green() < 14000 &&
                        colorsensor.red() < 8500 &&
                        colorsensor.blue() < 4500;

        double power = testMotor.getPower();


        // Remember that Pollen was seen
        if (isSeen) {
            wasSeen = true;
        }


        // Only count if:
        // 1. Pollen was seen FIRST
        // 2. The touch sensor is newly pressed
        if (wasSeen && isPressed && !wasPressed) {

            // Intake
            if (power > 0.5) {
                touchCount++;
            }

            // Outtake
            else if (power < -0.5) {
                touchCount--;
            }

            // Reset so the next Pollen can be counted
            wasSeen = false;
        }


        // Remember the button state
        wasPressed = isPressed;
    }


    public int returntouchcount() {

        return touchCount;
    }


    public void colors() {

        amountOfRed = colorsensor.red();
        amountOfGreen = colorsensor.green();
        amountOfBlue = colorsensor.blue();
    }


    public int returnred() {

        return amountOfRed;
    }


    public int returnblue() {

        return amountOfBlue;
    }


    public int returngreen() {

        return amountOfGreen;
    }
}