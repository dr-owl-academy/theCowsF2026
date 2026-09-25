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


    public sensors(HardwareMap hardwareMap) {

        touchsensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }


    public void ballCounter() {

        boolean isPressed = touchsensor.isPressed();

        double power = testMotor.getPower();

        if (isPressed && !wasPressed) {

            // Intake
            if (power > 0.5) {
                touchCount++;
            }

            // Outtake
            else if (power < -0.5) {
                touchCount--;
            }
        }

        // Remember the button state
        wasPressed = isPressed;
    }


    public int returntouchcount() {
        return touchCount;
    }


    public boolean senseColor() {

        // Get current color sensor readings
        amountOfRed = colorsensor.red();
        amountOfGreen = colorsensor.green();
        amountOfBlue = colorsensor.blue();


        // Pollen
        pollenSensed = amountOfGreen > 9500
                        && amountOfRed > 6000
                        && amountOfBlue > 2000
                        && amountOfGreen < 14000
                        && amountOfRed < 8500
                        && amountOfBlue < 4500;


        // Red Nectar
        redNectarSensed = amountOfBlue > 50
                && amountOfGreen > 2000
                && amountOfRed > 2800
                && amountOfBlue < 1500
                && amountOfGreen < 3200
                && amountOfRed < 5600;


        // Blue Nectar
        blueNectarSensed = amountOfBlue > 4900
                && amountOfGreen > 500
                && amountOfRed > 100
                && amountOfBlue < 6600
                && amountOfGreen < 1900
                && amountOfRed < 1300;


        // Return whether pollen was detected
        return pollenSensed;
    }


    public int returnblue() {
        return amountOfBlue;
    }


    public int returngreen() {
        return amountOfGreen;
    }


    public int returnred() {
        return amountOfRed;
    }


    public boolean returnPollen() {
        return pollenSensed;
    }


    public boolean returRedNectar() {
        return redNectarSensed;
    }


    public boolean returBlueNectar() {
        return blueNectarSensed;
    }

}