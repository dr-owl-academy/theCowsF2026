package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Sensors {

    private final TouchSensor touchSensor;

    private boolean previousTouchPressed = false;
    private int itemCount = 0;

    public Sensors(HardwareMap hardwareMap) {
        touchSensor =
                hardwareMap.get(TouchSensor.class, "touchSensor");
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