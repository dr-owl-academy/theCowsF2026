
package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "autonRedPath2")
public class autonRedPath2 extends OpMode {

    private DcMotor testMotor;

    private enum AutoState {
        SHOOT_THE_BALLS,
        WAIT_FOR_SHOOT,
        COMPLETE
    }

    private Follower follower;

    private PathChain driveToBalls1;
    private PathChain driveToBalls2;
    private PathChain driveToShot1;
    private PathChain driveToFlower1;
    private PathChain driveToFlower2;
    private PathChain driveToShot2;
    private PathChain driveToFinal1;
    private PathChain driveToFinal2;

    private AutoState autoState = AutoState.SHOOT_THE_BALLS;

    private static final Pose START_POSE =
            new Pose(48, 9, Math.toRadians(90));
    private static final Pose SHOOT_POSE =
            new Pose(56, 24, Math.toRadians(90));
    private static final Pose BALLS_POSE1 =
            new Pose(8.892706872370269, 28.975455820476874, Math.toRadians(270));
    private static final Pose BALLS_POSE2 =
            new Pose(8.83660589060309, 8.707573632538564, Math.toRadians(270));
    private static final Pose FLOWER_POSE1 =
            new Pose(18.97124824684433, 45.53786816269285, Math.toRadians(90));
    private static final Pose FLOWER_POSE2 =
            new Pose(11.030513176144249, 46.963938973647714, Math.toRadians(180));
    private static final Pose ALMOST_FINAL_POSE =
            new Pose(25.309292649098474, 51.33703190013869, Math.toRadians(175));
    private static final Pose FINAL_POSE =
            new Pose(11, 95, Math.toRadians(175));



    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(START_POSE);
        follower.setMaxPower(0.5);

        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        testMotor.setPower(0);

        buildPath();

        telemetry.addLine("Autonomous ready");
        telemetry.update();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        Pose currentPose = follower.getPose();

        telemetry.addData("X", currentPose.getX());
        telemetry.addData("Y", currentPose.getY());
        telemetry.addData("Heading", Math.toDegrees(currentPose.getHeading()));
        telemetry.addData("State", autoState);

        if (autoState == AutoState.COMPLETE) {
            telemetry.addLine("Autonomous complete");
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        if (testMotor != null) {
            testMotor.setPower(0);
        }
    }

    private void buildPath() {
        driveToBalls1 = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, BALLS_POSE1))
                .setLinearHeadingInterpolation(
                        Math.toRadians(90), Math.toRadians(270))
                .build();

        driveToBalls2 = follower.pathBuilder()
                .addPath(new BezierLine(BALLS_POSE1, BALLS_POSE2))
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        driveToShot1 = follower.pathBuilder()
                .addPath(new BezierLine(BALLS_POSE2, SHOOT_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        driveToFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(SHOOT_POSE, FLOWER_POSE1))
                .setConstantHeadingInterpolation(Math.toRadians(150))
                .build();

        driveToFlower2 = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE1, FLOWER_POSE2))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        driveToShot2 = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE2, SHOOT_POSE))
                .setLinearHeadingInterpolation(
                        Math.toRadians(180), Math.toRadians(90))
                .build();

        driveToFinal1 = follower.pathBuilder()
                .addPath(new BezierLine(SHOOT_POSE, ALMOST_FINAL_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(139))
                .build();

        driveToFinal2 = follower.pathBuilder()
                .addPath(new BezierLine(ALMOST_FINAL_POSE, FINAL_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(175))
                .build();
    }

    private void autonomousPathUpdate() {
        switch (autoState) {


            case COMPLETE:
                testMotor.setPower(0);
                break;
        }
    }
}
