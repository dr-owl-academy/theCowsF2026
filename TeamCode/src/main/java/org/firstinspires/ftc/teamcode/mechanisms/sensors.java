package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class sensors {
    private final TouchSensor touchSensor;
    private final ColorSensor colorSensor;
    private double power;
    private final DcMotor testMotor;
    private boolean wasPressed = false;
    private int Counter = 0;
    public sensors(HardwareMap hardwareMap) {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchsensor");
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");
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
    public int colorDetector(){
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.update();
        return 0;
    }
}