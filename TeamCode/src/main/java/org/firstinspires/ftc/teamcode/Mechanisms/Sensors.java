package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Sensors {

    private final TouchSensor touchSensor;
    private ColorSensor colorSensor;

    private boolean previousTouchPressed = false;
    private int itemCount = 0;
    public boolean seesYellow() {
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();

        return (red > blue) && (green > blue);
    }

    public boolean seesGreen(){
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();

        return (green > red) && (green > blue);
    }


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
        if (seesGreen()) {
            outtaking = true;  // Outtake
        }
        else if (seesYellow()) {
            
            intaking = true; // intake
        }
        else {

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