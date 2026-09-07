package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.sensors;

            @TeleOp(name="felix_Sensor_Test")
            public class felix_Sensor_Test extends OpMode {

                private sensors sensors;
                private DcMotor testMotor;
                private ColorSensor colorSensor;


                @Override
                public void init() {
                    sensors = new sensors(hardwareMap);
                    testMotor = hardwareMap.get(DcMotor.class, "testMotor");
                    colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");
                }

                @Override
                public void loop() {
                    sensors.TouchCounter();



                    if (gamepad1.b) {
                        testMotor.setPower(1);
                    } else if (gamepad1.a) {
                        testMotor.setPower(-1);
                     }
                    else{
                        testMotor.setPower(0);
                    }
                    telemetry.addData("Touch Count", sensors.returntouchcount());
                    telemetry.update();
    }
}

