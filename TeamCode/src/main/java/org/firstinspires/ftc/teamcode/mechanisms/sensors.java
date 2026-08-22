package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class sensors {

    private TouchSensor touchSensor;

    private int touchCount = 0;
    private boolean wasPressed = false;

    public sensors(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
    }

    // Call this repeatedly in your TeleOp loop.
    // Returns the total number of times the sensor has been pressed.
    public int countTouchSensorPresses() {

        boolean isPressed = touchSensor.isPressed();

        // Count only when the sensor changes from
        // not pressed -> pressed
        if (isPressed && !wasPressed) {
            touchCount++;
        }

        wasPressed = isPressed;

        return touchCount;
    }
}