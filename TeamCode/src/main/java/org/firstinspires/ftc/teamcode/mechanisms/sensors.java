package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class sensors {
    private TouchSensor touchSensor;
    private boolean wasPressed = false;
    private int Counter = 0;
    public sensors(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
    }

    public int TouchCounter() {

    // send the info back to driver station using telemetry function.
        if(touchSensor.isPressed() && !wasPressed) {
            Counter++;
        }
        wasPressed = touchSensor.isPressed();
    return Counter;
    }
}