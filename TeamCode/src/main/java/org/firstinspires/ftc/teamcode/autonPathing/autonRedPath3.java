
package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "autonRedPath3")
public class autonRedPath3 extends OpMode {

    private enum AutoState {
        SHOT1,
        WAIT_FOR_SHOT1,
        DRIVE_TO_PREFLOWER1,
        WAIT_FOR_PATH1,
        DRIVE_TO_FLOWER1,
        WAIT_FOR_PATH2,
        INTAKE1,
        WAIT_FOR_INTAKE1,
        DRIVE_PATH3,
        WAIT_FOR_PATH3,
        DRIVE_ACROSS,
        WAIT_FOR_PATH4,
        DRIVE_TO_PREFLOWER2,
        WAIT_FOR_PATH5,
        SHOT2,
        WAIT_FOR_SHOT2,
        TURN_TO_90,
        WAIT_FOR_TURN1,
        DRIVE_TO_FLOWER2,
        WAIT_FOR_PATH6,
        INTAKE_BALLS2,
        WAIT_FOR_INTAKE,
        DRIVE_BACK,
        WAIT_FOR_PATH7,
        TURN_TO_270,
        WAIT_FOR_TURN2,
        SHOT3,
        WAIT_FOR_SHOT3,
        DRIVE_TO_FINAL,
        WAIT_FOR_PATH8,
        COMPLETE
    }

    private Follower follower;

    private PathChain driveToPreFlower1;
    private PathChain driveToFlower1;
    private PathChain path3;
    private PathChain driveToOtherSide;
    private PathChain driveToImportantSpot;
    private PathChain driveToFlower2;
    private PathChain driveBackToImportantSpot;
    private PathChain driveToFinal;

    private AutoState autoState = AutoState.SHOT1;

    private long stateStartTime;

    private static final long SHOOT_TIME = 1000;
    private static final long INTAKE_TIME = 1500;

    private static final Pose START_POSE =
            new Pose(48, 9, Math.toRadians(90));

    private static final Pose PREFLOWER_POSE1 =
            new Pose(25.75, 47.54, Math.toRadians(180));

    private static final Pose FLOWER_POSE1 =
            new Pose(9.67, 47.37, Math.toRadians(180));

    private static final Pose CROSSOTHERSIDEPOSE =
            new Pose(25.75, 47.54, Math.toRadians(180));

    private static final Pose OTHERSIDEPOSE =
            new Pose(26.51, 115.91, Math.toRadians(270));

    private static final Pose IMPORTANTPOSE =
            new Pose(47.93, 115.53, Math.toRadians(90));

    private static final Pose FLOWER2POSE =
            new Pose(46.57, 132.1, Math.toRadians(270));

    private static final Pose FINAL_POSE =
            new Pose(9.79, 118.16, Math.toRadians(180));

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
    public void start() {
        setState(AutoState.SHOT1);
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
        telemetry.addData("Follower Busy", follower.isBusy());

        if (autoState == AutoState.COMPLETE) {
            telemetry.addLine("Autonomous complete");
        }

        telemetry.update();
    }

    @Override
    public void stop() {
    }

    private void setState(AutoState newState) {
        autoState = newState;
        stateStartTime = System.currentTimeMillis();
    }

    private boolean stateTimerFinished(long duration) {
        return System.currentTimeMillis() - stateStartTime >= duration;
    }

    private void buildPath() {

        driveToPreFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, PREFLOWER_POSE1))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        driveToFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(PREFLOWER_POSE1, FLOWER_POSE1))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        path3 = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE1, CROSSOTHERSIDEPOSE))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        driveToOtherSide = follower.pathBuilder()
                .addPath(new BezierLine(CROSSOTHERSIDEPOSE, OTHERSIDEPOSE))
                .setLinearHeadingInterpolation(
                        Math.toRadians(180),
                        Math.toRadians(270)
                )
                .build();

        driveToImportantSpot = follower.pathBuilder()
                .addPath(new BezierLine(OTHERSIDEPOSE, IMPORTANTPOSE))
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        driveToFlower2 = follower.pathBuilder()
                .addPath(new BezierLine(IMPORTANTPOSE, FLOWER2POSE))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        driveBackToImportantSpot = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER2POSE, IMPORTANTPOSE))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        driveToFinal = follower.pathBuilder()
                .addPath(new BezierLine(IMPORTANTPOSE, FINAL_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    private void autonomousPathUpdate() {

        switch (autoState) {

            case SHOT1:
                // TODO: Run shooter motor
                setState(AutoState.WAIT_FOR_SHOT1);
                break;

            case WAIT_FOR_SHOT1:
                if (stateTimerFinished(SHOOT_TIME)) {
                    setState(AutoState.DRIVE_TO_PREFLOWER1);
                }
                break;

            case DRIVE_TO_PREFLOWER1:
                follower.followPath(driveToPreFlower1);
                setState(AutoState.WAIT_FOR_PATH1);
                break;

            case WAIT_FOR_PATH1:
                if (!follower.isBusy()) {
                    setState(AutoState.DRIVE_TO_FLOWER1);
                }
                break;

            case DRIVE_TO_FLOWER1:
                follower.followPath(driveToFlower1);
                setState(AutoState.WAIT_FOR_PATH2);
                break;

            case WAIT_FOR_PATH2:
                if (!follower.isBusy()) {
                    setState(AutoState.INTAKE1);
                }
                break;

            case INTAKE1:
                // TODO: Run intake motor
                setState(AutoState.WAIT_FOR_INTAKE1);
                break;

            case WAIT_FOR_INTAKE1:
                if (stateTimerFinished(INTAKE_TIME)) {
                    setState(AutoState.DRIVE_PATH3);
                }
                break;

            case DRIVE_PATH3:
                follower.followPath(path3);
                setState(AutoState.WAIT_FOR_PATH3);
                break;

            case WAIT_FOR_PATH3:
                if (!follower.isBusy()) {
                    setState(AutoState.DRIVE_ACROSS);
                }
                break;

            case DRIVE_ACROSS:
                follower.followPath(driveToOtherSide);
                setState(AutoState.WAIT_FOR_PATH4);
                break;

            case WAIT_FOR_PATH4:
                if (!follower.isBusy()) {
                    setState(AutoState.DRIVE_TO_PREFLOWER2);
                }
                break;

            case DRIVE_TO_PREFLOWER2:
                follower.followPath(driveToImportantSpot);
                setState(AutoState.WAIT_FOR_PATH5);
                break;

            case WAIT_FOR_PATH5:
                if (!follower.isBusy()) {
                    setState(AutoState.SHOT2);
                }
                break;

            case SHOT2:
                // TODO: Run shooter motor
                setState(AutoState.WAIT_FOR_SHOT2);
                break;

            case WAIT_FOR_SHOT2:
                if (stateTimerFinished(SHOOT_TIME)) {
                    setState(AutoState.TURN_TO_90);
                }
                break;

            case TURN_TO_90:
                follower.turnTo(Math.toRadians(90));
                setState(AutoState.WAIT_FOR_TURN1);
                break;

            case WAIT_FOR_TURN1:
                if (!follower.isBusy()) {
                    setState(AutoState.DRIVE_TO_FLOWER2);
                }
                break;

            case DRIVE_TO_FLOWER2:
                follower.followPath(driveToFlower2);
                setState(AutoState.WAIT_FOR_PATH6);
                break;

            case WAIT_FOR_PATH6:
                if (!follower.isBusy()) {
                    setState(AutoState.INTAKE_BALLS2);
                }
                break;

            case INTAKE_BALLS2:
                // TODO: Run intake motor
                setState(AutoState.WAIT_FOR_INTAKE);
                break;

            case WAIT_FOR_INTAKE:
                if (stateTimerFinished(INTAKE_TIME)) {
                    setState(AutoState.DRIVE_BACK);
                }
                break;

            case DRIVE_BACK:
                follower.followPath(driveBackToImportantSpot);
                setState(AutoState.WAIT_FOR_PATH7);
                break;

            case WAIT_FOR_PATH7:
                if (!follower.isBusy()) {
                    setState(AutoState.TURN_TO_270);
                }
                break;

            case TURN_TO_270:
                follower.turnTo(Math.toRadians(270));
                setState(AutoState.WAIT_FOR_TURN2);
                break;

            case WAIT_FOR_TURN2:
                if (!follower.isBusy()) {
                    setState(AutoState.SHOT3);
                }
                break;

            case SHOT3:
                // TODO: Run shooter motor
                setState(AutoState.WAIT_FOR_SHOT3);
                break;

            case WAIT_FOR_SHOT3:
                if (stateTimerFinished(SHOOT_TIME)) {
                    setState(AutoState.DRIVE_TO_FINAL);
                }
                break;

            case DRIVE_TO_FINAL:
                follower.followPath(driveToFinal);
                setState(AutoState.WAIT_FOR_PATH8);
                break;

            case WAIT_FOR_PATH8:
                if (!follower.isBusy()) {
                    setState(AutoState.COMPLETE);
                }
                break;

            case COMPLETE:
                break;
        }
    }
}
