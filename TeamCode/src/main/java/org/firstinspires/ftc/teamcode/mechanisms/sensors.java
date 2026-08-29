package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class sensors {

    private TouchSensor touchSensor;
    private boolean wasPressed = false;
    private int counter = 0;


    public sensors(HardwareMap hardwareMap){
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
    }
    public int countpresses() {
        boolean isPressed = touchSensor.isPressed();
        if (isPressed && !wasPressed) {
            counter++;

            wasPressed = isPressed;
        }
        return counter;
    }

}

