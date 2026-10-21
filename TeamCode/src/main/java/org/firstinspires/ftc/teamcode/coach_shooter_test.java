package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class coach_shooter_test extends OpMode {

    private DcMotorEx flywheel;
    private ServoImplEx hoodServo;

    private static final double RPM_STEP = 100.0;
    private static final double HOOD_STEP = 0.01;

    // goBILDA 5000-0002-4008: 28 quadrature counts per shaft revolution.
    // 6000 RPM theoretical / 5800 RPM tested no-load at 12V.
    // The loaded shooter may not reach this command ceiling.
    private static final double TICKS_PER_REVOLUTION = 28.0;
    private static final double MAX_RPM = 6000.0;

    // goBILDA 2000-0025-0002 in positional (servo) mode.
    // With 500-2500 us PWM, 0.0-1.0 spans about 300 servo shaft degrees.
    // Narrow these limits to the actual hood linkage's usable travel.
    private static final double HOOD_MIN = 0.0;
    private static final double HOOD_MAX = 1.0;
    private double hoodPosition = 0.5;

    private double targetRPM = 0.0;

    private boolean previousUp, previousDown, previousLeft, previousRight;

    @Override
    public void init() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        hoodServo = hardwareMap.get(ServoImplEx.class, "Servo");
        hoodServo.setPwmRange(new PwmControl.PwmRange(500, 2500));

        // Change to REVERSE if the flywheel spins the wrong way.
        flywheel.setDirection(DcMotor.Direction.REVERSE);
        flywheel.setPower(0.0);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Future tuning: replace P, I, D, F with numbers and uncomment.
        // flywheel.setVelocityPIDFCoefficients(P, I, D, F);

        // Configure a matching 28-count/rev, 6000-RPM (1:1) motor profile
        // in the robot configuration, and connect its encoder cable to
        // the same numbered Hub port as the motor's power cable.
        // Displayed RPM is motor shaft RPM; flywheel RPM matches for 1:1 drive.

        hoodServo.setPosition(hoodPosition);
        telemetry.addLine("Ready: D-pad up/down = RPM; left/right = hood");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Rising-edge detection: one adjustment per press.
        if (gamepad1.dpad_up && !previousUp) {
            targetRPM += RPM_STEP;
        }
        if (gamepad1.dpad_down && !previousDown) {
            targetRPM -= RPM_STEP;
        }
        if (gamepad1.dpad_right && !previousRight) {
            hoodPosition += HOOD_STEP;
        }
        if (gamepad1.dpad_left && !previousLeft) {
            hoodPosition -= HOOD_STEP;
        }

        targetRPM = Range.clip(targetRPM, 0.0, MAX_RPM);
        hoodPosition = Range.clip(hoodPosition, HOOD_MIN, HOOD_MAX);

        // setVelocity() and getVelocity() use encoder ticks per second.
        flywheel.setVelocity(targetRPM * TICKS_PER_REVOLUTION / 60.0);
        hoodServo.setPosition(hoodPosition);

        double actualRPM = flywheel.getVelocity() * 60.0 / TICKS_PER_REVOLUTION;

        telemetry.addData("Target speed (RPM)", Math.round(targetRPM));
        telemetry.addData("Actual speed (RPM)", Math.round(actualRPM));
        telemetry.addData("Speed error (RPM)", Math.round(targetRPM - actualRPM));
        telemetry.addData("Hood position", Math.round(hoodServo.getPosition() * 100.0) / 100.0);
        telemetry.update();

        previousUp = gamepad1.dpad_up;
        previousDown = gamepad1.dpad_down;
        previousLeft = gamepad1.dpad_left;
        previousRight = gamepad1.dpad_right;
    }

    @Override
    public void stop() {
        flywheel.setVelocity(0.0);
        flywheel.setPower(0.0);
    }
}
