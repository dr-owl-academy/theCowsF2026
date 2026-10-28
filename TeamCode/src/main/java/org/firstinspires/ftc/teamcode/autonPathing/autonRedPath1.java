
package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "autonRedPath1")
public class autonRedPath1 extends OpMode {

    private DcMotor testMotor;

    private enum AutoState {
        SHOOT_THE_BALLS,
        WAIT_FOR_SHOOT,
        DRIVE_TO_BALLS_PART1,
        WAIT_FOR_PATH1,
        DRIVE_TO_BALLS_PART2,
        WAIT_FOR_PATH2,
        INTAKE,
        WAIT_FOR_INTAKE1,
        DRIVE_BACK_TO_SHOT,
        WAIT_FOR_PATH3,
        TURN_TO_ZERO,
        WAIT_FOR_TURN,
        SHOOT_THE_BALLS2,
        WAIT_FOR_SHOOT2,
        DRIVE_TO_FLOWER1,
        WAIT_FOR_PATH4,
        DRIVE_TO_FLOWER2,
        WAIT_FOR_PATH5,
        INTAKE2,
        WAIT_FOR_INTAKE2,
        DRIVE_BACK_TO_SHOT2,
        WAIT_FOR_PATH6,
        SHOOT_THE_BALLS3,
        WAIT_FOR_SHOOT3,
        DRIVE_TO_FINAL1,
        WAIT_FOR_PATH7,
        DRIVE_TO_FINAL2,
        WAIT_FOR_PATH8,
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

            case SHOOT_THE_BALLS:
                testMotor.setPower(1.0);
                autoState = AutoState.WAIT_FOR_SHOOT;
                break;

            case WAIT_FOR_SHOOT:
                    testMotor.setPower(0);
                    autoState = AutoState.DRIVE_TO_BALLS_PART1;

                break;

            case DRIVE_TO_BALLS_PART1:
                follower.followPath(driveToBalls1, true);
                autoState = AutoState.WAIT_FOR_PATH1;
                break;

            case WAIT_FOR_PATH1:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_BALLS_PART2;
                }
                break;

            case DRIVE_TO_BALLS_PART2:
                follower.followPath(driveToBalls2, true);
                autoState = AutoState.WAIT_FOR_PATH2;
                break;

            case WAIT_FOR_PATH2:
                if (!follower.isBusy()) {
                    autoState = AutoState.INTAKE;
                }
                break;

            case INTAKE:
                testMotor.setPower(1.0);
                autoState = AutoState.WAIT_FOR_INTAKE1;
                break;

            case WAIT_FOR_INTAKE1:
                    testMotor.setPower(0);
                    autoState = AutoState.DRIVE_BACK_TO_SHOT;

                break;

            case DRIVE_BACK_TO_SHOT:
                follower.followPath(driveToShot1, true);
                autoState = AutoState.WAIT_FOR_PATH3;
                break;

            case WAIT_FOR_PATH3:
                if (!follower.isBusy()) {
                    autoState = AutoState.TURN_TO_ZERO;
                }
                break;

            case TURN_TO_ZERO:
                follower.turnTo(Math.toRadians(0));
                autoState = AutoState.WAIT_FOR_TURN;
                break;

            case WAIT_FOR_TURN:
                if (!follower.isBusy()) {
                    autoState = AutoState.SHOOT_THE_BALLS2;
                }
                break;

            case SHOOT_THE_BALLS2:
                testMotor.setPower(1.0);
                autoState = AutoState.WAIT_FOR_SHOOT2;
                break;

            case WAIT_FOR_SHOOT2:
                    testMotor.setPower(0);
                    autoState = AutoState.DRIVE_TO_FLOWER1;

                break;

            case DRIVE_TO_FLOWER1:
                follower.followPath(driveToFlower1, true);
                autoState = AutoState.WAIT_FOR_PATH4;
                break;

            case WAIT_FOR_PATH4:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_FLOWER2;
                }
                break;

            case DRIVE_TO_FLOWER2:
                follower.followPath(driveToFlower2, true);
                autoState = AutoState.WAIT_FOR_PATH5;
                break;

            case WAIT_FOR_PATH5:
                if (!follower.isBusy()) {
                    autoState = AutoState.INTAKE2;
                }
                break;

            case INTAKE2:
                testMotor.setPower(1.0);
                autoState = AutoState.WAIT_FOR_INTAKE2;
                break;

            case WAIT_FOR_INTAKE2:
                    testMotor.setPower(0);
                    autoState = AutoState.DRIVE_BACK_TO_SHOT2;
                break;

            case DRIVE_BACK_TO_SHOT2:
                follower.followPath(driveToShot2, true);
                autoState = AutoState.WAIT_FOR_PATH6;
                break;

            case WAIT_FOR_PATH6:
                if (!follower.isBusy()) {
                    autoState = AutoState.SHOOT_THE_BALLS3;
                }
                break;

            case SHOOT_THE_BALLS3:
                testMotor.setPower(1.0);
                autoState = AutoState.WAIT_FOR_SHOOT3;
                break;

            case WAIT_FOR_SHOOT3:
                    testMotor.setPower(0);
                    autoState = AutoState.DRIVE_TO_FINAL1;
                break;

            case DRIVE_TO_FINAL1:
                follower.followPath(driveToFinal1, true);
                autoState = AutoState.WAIT_FOR_PATH7;
                break;

            case WAIT_FOR_PATH7:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_FINAL2;
                }
                break;

            case DRIVE_TO_FINAL2:
                follower.followPath(driveToFinal2, true);
                autoState = AutoState.WAIT_FOR_PATH8;
                break;

            case WAIT_FOR_PATH8:
                if (!follower.isBusy()) {
                    autoState = AutoState.COMPLETE;
                }
                break;

            case COMPLETE:
                testMotor.setPower(0);
                break;
        }
    }
}
