package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Test")
public class MotorTest extends OpMode {

    private DcMotor motor;
    private Sensors sensors;

    private final double motorSpeed = 0.2;

    private boolean intaking = false;
    private boolean outtaking = false;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        sensors = new Sensors(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            intaking = true;
            outtaking = false;
            motor.setPower(motorSpeed);
        } else if (gamepad1.left_bumper) {
            intaking = false;
            outtaking = true;
            motor.setPower(-motorSpeed);
        } else {
            intaking = false;outtaking = false;
            motor.setPower(0.0);
        }

        sensors.updateItemCount(intaking, outtaking);

    }
}