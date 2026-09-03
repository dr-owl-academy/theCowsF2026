package org.firstinspires.ftc.teamcode.Mechanisms;import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class sensorMK {
    private TouchSensor touchSensor;
    private boolean wasPressed = false;

    private int touchcount = 0;
    public sensorMK(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class,"touchsensor");
    }

    public int countTouchSensorPresses() {
        boolean isPressed = touchSensor.isPressed();

        if (isPressed && !wasPressed) {
            touchcount++;
        }
        wasPressed=isPressed;
        return touchcount;
    }
}
