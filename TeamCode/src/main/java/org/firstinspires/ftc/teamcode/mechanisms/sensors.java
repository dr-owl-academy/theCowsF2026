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

    public void update() {
        boolean isPressed = touchSensor.isPressed();

        if (isPressed && !wasPressed) {
            touchCount++;
        }

        wasPressed = isPressed;
    }

    public int TouchCounter() {
        return touchCount;
    }
}