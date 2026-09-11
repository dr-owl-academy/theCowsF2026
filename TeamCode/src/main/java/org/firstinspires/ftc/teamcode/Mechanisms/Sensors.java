package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Sensors {

    private final TouchSensor touchSensor;
    private ColorSensor colorSensor;

    private boolean previousTouchPressed = false;
    private int itemCount = 0;

    public boolean seesGreen = (colorSensor.green() > 9500 && colorSensor.green() < 14000 && colorSensor.red() > 6000 && colorSensor.red() < 8500 && colorSensor.blue() > 2000 && colorSensor.blue() < 4500);
    public Sensors(HardwareMap hardwareMap) {
        touchSensor =
                hardwareMap.get(TouchSensor.class, "touchSensor");
        colorSensor =
                hardwareMap.get(ColorSensor.class, "color sensor");
    }

    public void updateItemCount(boolean intaking, boolean outtaking) {
        boolean touchPressed = touchSensor.isPressed();

        // Only count a new press, not every loop while held
        if (touchPressed && !previousTouchPressed) {
            if (intaking) {
                itemCount++;
            } else if (outtaking) {
                itemCount--;
                if (itemCount < 0) {
                    itemCount = 0;
                }
            }
        }
        if (seesGreen) {
            intaking = true;  // intake

        }
        previousTouchPressed = touchPressed;
    }


    public int getItemCount() {
        return itemCount;
    }

    public boolean isTouchSensorPressed() {
        return touchSensor.isPressed();
    }

    public void resetItemCount() {
        itemCount = 0;
    }
}