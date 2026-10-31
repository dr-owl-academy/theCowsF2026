package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Flywheel Tuner")
public class FlywheelTuner extends OpMode {

    // Hardware and Tuning Variables
    public DcMotorEx flywheelMotor;
    public double highVelocity = 1500;
    public double lowVelocity = 900;
    public double curTargetVelocity = highVelocity;

    // PIDF Constants (Tuned on the fly)
    public double p = 0.0;
    public double f = 15.58;

    // Step adjustment sensitivity array
    public double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001};
    public int stepIndex = 1; // Starts at index 1 (1.0 increments)

    @Override
    public void init() {
        // Hardware Mapping
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheel");

        // Configuration
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheelMotor.setDirection(DcMotor.Direction.REVERSE);

        // Initial coefficient profile deployment
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(p, 0, 0, f);
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        telemetry.addLine("Init Complete");
    }

    @Override
    public void loop() {
        // 1. Toggle Target Velocity (High vs Low)
        if (gamepad1.y) { // SDK auto edge detection for "wasPressed" functionality
            if (curTargetVelocity == highVelocity) {
                curTargetVelocity = lowVelocity;
            } else {
                curTargetVelocity = highVelocity;
            }
        }

        // 2. Cycle Through Step Sensitivity Sizes
        if (gamepad1.b) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        // 3. Adjust Feedforward (F Constant) - Left/Right D-pad
        if (gamepad1.dpad_left) {
            f -= stepSizes[stepIndex];
        }
        if (gamepad1.dpad_right) {
            f += stepSizes[stepIndex];
        }

        // 4. Adjust Proportional (P Constant) - Up/Down D-pad
        if (gamepad1.dpad_up) {
            p += stepSizes[stepIndex];
        }
        if (gamepad1.dpad_down) {
            p -= stepSizes[stepIndex];
        }

        // 5. Update Coefficients & Set Motor Target
        PIDFCoefficients newPIDF = new PIDFCoefficients(p, 0, 0, f);
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, newPIDF);
        flywheelMotor.setVelocity(curTargetVelocity);

        // 6. Gather Performance Data
        double curVelocity = flywheelMotor.getVelocity();
        double error = curTargetVelocity - curVelocity;

        // 7. Push Telemetry to Driver Station Dashboard
        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Actual Velocity", curVelocity);
        telemetry.addData("Absolute Error", error);
        telemetry.addData("Tuning P", p);
        telemetry.addData("Tuning F", f);
        telemetry.addData("Current Step Size", stepSizes[stepIndex]);
        telemetry.update();
    }
}