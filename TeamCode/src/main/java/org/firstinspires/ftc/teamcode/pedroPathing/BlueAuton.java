package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "BlueAuton")
public class BlueAuton extends OpMode {

    private enum AutoState {
        SHOOT,
        WAIT_FOR_SHOOT,
        DRIVE_TO_FLOWER,
        WAIT_FOR_PATH1,
        DRIVE_TO_SHOT,
        WAIT_FOR_PATH2,
        SHOOT2,
        WAIT_FOR_SHOOT2,
        DRIVE_TO_FLOWER2,
        WAIT_FOR_PATH3,
        DRIVE_TO_PREFINAL,
        WAIT_FOR_PATH4,
        DRIVE_TO_FINAL,
        WAIT_FOR_PATH5,
        COMPLETE
    }

    private Follower follower;

    private PathChain driveToFlower1;
    private PathChain driveToPreshoot;
    private PathChain driveToShot;
    private PathChain driveToFlower2;
    private PathChain driveToPreFinal;
    private PathChain driveToFinal;

    private AutoState autoState = AutoState.SHOOT;

    private static final Pose START_POSE =
            new Pose(96, 133, Math.toRadians(270));
    private static final Pose FLOWER_POSE1 =
            new Pose(128, 94.33, Math.toRadians(0));
    private static final Pose PRESHOOT_POSE1 =
            new Pose(112, 31, Math.toRadians(90));
    private static final Pose SHOOT_POSE =
            new Pose(83.5, 31, Math.toRadians(90));
    private static final Pose FLOWER_POSE2 =
            new Pose(94.33, 13.5, Math.toRadians(270));
    private static final Pose ALMOST_FINAL_POSE =
            new Pose(83.5, 21.5, Math.toRadians(90));
    private static final Pose FINAL_POSE =
            new Pose(130, 35, Math.toRadians(90));

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(START_POSE);
        follower.setMaxPower(0.5);

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
        telemetry.addData(
                "Heading",
                Math.toDegrees(currentPose.getHeading())
        );
        telemetry.addData("State", autoState);

        if (autoState == AutoState.COMPLETE) {
            telemetry.addLine("Autonomous complete");
        }

        telemetry.update();
    }

    @Override
    public void stop() {

    }

    private void buildPath() {
        driveToFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, FLOWER_POSE1))
                .setLinearHeadingInterpolation(
                        Math.toRadians(270),
                        Math.toRadians(0)
                )
                .build();

        driveToPreshoot = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE1, PRESHOOT_POSE1))
                .setLinearHeadingInterpolation(
                        Math.toRadians(0),
                        Math.toRadians(90)
                )
                .build();

        driveToShot = follower.pathBuilder()
                .addPath(new BezierLine(PRESHOOT_POSE1, SHOOT_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        driveToFlower2 = follower.pathBuilder()
                .addPath(new BezierLine(SHOOT_POSE, FLOWER_POSE2))
                .setLinearHeadingInterpolation(
                        Math.toRadians(90),
                        Math.toRadians(270)
                )
                .build();

        driveToPreFinal = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE2, ALMOST_FINAL_POSE))
                .setLinearHeadingInterpolation(
                        Math.toRadians(270),
                        Math.toRadians(90)
                )
                .build();

        driveToFinal = follower.pathBuilder()
                .addPath(new BezierLine(ALMOST_FINAL_POSE, FINAL_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
    }

    private void autonomousPathUpdate() {
        switch (autoState) {
            case SHOOT:
                // No shooter hardware behavior defined.
                autoState = AutoState.WAIT_FOR_SHOOT;
                break;

            case WAIT_FOR_SHOOT:
                // Placeholder: no shooting completion condition defined.
                autoState = AutoState.DRIVE_TO_FLOWER;
                break;

            case DRIVE_TO_FLOWER:
                follower.followPath(driveToFlower1);
                autoState = AutoState.WAIT_FOR_PATH1;
                break;

            case WAIT_FOR_PATH1:
                if (!follower.isBusy()) {
                    follower.followPath(driveToPreshoot);
                    autoState = AutoState.DRIVE_TO_SHOT;
                }
                break;

            case DRIVE_TO_SHOT:
                // Finish the preshoot path before starting the shot path.
                if (!follower.isBusy()) {
                    follower.followPath(driveToShot);
                    autoState = AutoState.WAIT_FOR_PATH2;
                }
                break;

            case WAIT_FOR_PATH2:
                if (!follower.isBusy()) {
                    autoState = AutoState.SHOOT2;
                }
                break;

            case SHOOT2:
                // No shooter hardware behavior defined.
                autoState = AutoState.WAIT_FOR_SHOOT2;
                break;

            case WAIT_FOR_SHOOT2:
                // Placeholder: no shooting completion condition defined.
                autoState = AutoState.DRIVE_TO_FLOWER2;
                break;

            case DRIVE_TO_FLOWER2:
                follower.followPath(driveToFlower2);
                autoState = AutoState.WAIT_FOR_PATH3;
                break;

            case WAIT_FOR_PATH3:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_PREFINAL;
                }
                break;

            case DRIVE_TO_PREFINAL:
                follower.followPath(driveToPreFinal);
                autoState = AutoState.WAIT_FOR_PATH4;
                break;

            case WAIT_FOR_PATH4:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_FINAL;
                }
                break;

            case DRIVE_TO_FINAL:
                follower.followPath(driveToFinal);
                autoState = AutoState.WAIT_FOR_PATH5;
                break;

            case WAIT_FOR_PATH5:
                if (!follower.isBusy()) {
                    autoState = AutoState.COMPLETE;
                }
                break;

            case COMPLETE:
                break;
        }
    }
}