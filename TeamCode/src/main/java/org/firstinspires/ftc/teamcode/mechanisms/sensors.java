package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class sensors {
    private final TouchSensor touchSensor;
    private double power;
    private DcMotor testMotor;
    private boolean wasPressed = false;
    private int Counter = 0;
    public sensors(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }

    public int TouchCounter() {

    // send the info back to driver station using telemetry function.
        if(touchSensor.isPressed() && !wasPressed) {
            power = testMotor.getPower();
            if(power > 0.5){
                Counter++;
            }
            if(power < -0.5) {
                Counter--;
            }
        }
        wasPressed = touchSensor.isPressed();
    return Counter;
    }
}