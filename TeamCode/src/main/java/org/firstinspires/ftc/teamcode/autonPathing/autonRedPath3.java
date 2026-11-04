
package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "autonRedPath3")
public class autonRedPath3 extends OpMode {

    private enum AutoState {
        SHOOT_THE_BALLS,
        WAIT_FOR_SHOOT,
        DRIVE_TO_FLOWER1,
        WAIT_FOR_PATH1,
        DRIVE_TO_FLOWER2,
        WAIT_FOR_PATH2,
        INTAKE_BALLS_FLOWER,
        WAIT_FOR_INTAKE,
        DRIVE_TO_SHOT,
        WAIT_FOR_PATH3,
        SHOOT_THE_BALLS2,
        WAIT_FOR_SHOOT2,
        DRIVE_TO_NECTAR1,
        WAIT_FOR_PATH4,
        DRIVE_TO_NECTAR2,
        WAIT_FOR_PATH5,
        DRIVE_TO_PRESHOT2,
        WAIT_FOR_PATH6,
        DRIVE_TO_SHOT2,
        WAIT_FOR_PATH7,
        SHOOT_THE_BALLS3,
        WAIT_FOR_SHOOT3,
        DRIVE_TO_PRE_FINAL,
        WAIT_FOR_PATH8,
        DRIVE_TO_FINAL,
        WAIT_FOR_PATH9,
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

    private AutoState autoState = AutoState.SHOOT_THE_BALLS;

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
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        Pose currentPose = follower.getPose();

        telemetry.addData("X", currentPose.getX());
        telemetry.addData("Y", currentPose.getY());
        telemetry.addData("Heading", Math.toDegrees(currentPose.getHeading()));
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

    private void buildPath() {
        driveToPreFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, PREFLOWER_POSE1))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        driveToPreFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, PREFLOWER_POSE1))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();




    }

    private void autonomousPathUpdate() {
        switch (autoState) {

            case SHOOT_THE_BALLS:
                autoState = AutoState.WAIT_FOR_SHOOT;
                break;

            case WAIT_FOR_SHOOT:
                autoState = AutoState.DRIVE_TO_FLOWER1;
                break;

            case DRIVE_TO_FLOWER1:
                follower.setMaxPower(0.5);
                follower.followPath(driveToFlower1, true);
                autoState = AutoState.WAIT_FOR_PATH1;
                break;

            case WAIT_FOR_PATH1:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_FLOWER2;
                }
                break;

            case DRIVE_TO_FLOWER2:
                follower.followPath(driveToFlower2, true);
                autoState = AutoState.WAIT_FOR_PATH2;
                break;

            case WAIT_FOR_PATH2:
                if (!follower.isBusy()) {
                    autoState = AutoState.INTAKE_BALLS_FLOWER;
                }
                break;

            case INTAKE_BALLS_FLOWER:
                autoState = AutoState.WAIT_FOR_INTAKE;
                break;

            case WAIT_FOR_INTAKE:
                autoState = AutoState.DRIVE_TO_SHOT;
                break;

            case DRIVE_TO_SHOT:
                follower.followPath(driveToShot1, true);
                autoState = AutoState.WAIT_FOR_PATH3;
                break;

            case WAIT_FOR_PATH3:
                if (!follower.isBusy()) {
                    autoState = AutoState.SHOOT_THE_BALLS2;
                }
                break;

            case SHOOT_THE_BALLS2:
                autoState = AutoState.WAIT_FOR_SHOOT2;
                break;

            case WAIT_FOR_SHOOT2:
                autoState = AutoState.DRIVE_TO_NECTAR1;
                break;

            case DRIVE_TO_NECTAR1:
                follower.setMaxPower(0.5);
                follower.followPath(driveToNectar1, true);
                autoState = AutoState.WAIT_FOR_PATH4;
                break;

            case WAIT_FOR_PATH4:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_NECTAR2;
                }
                break;

            case DRIVE_TO_NECTAR2:
                follower.setMaxPower(0.25);
                follower.followPath(driveToNectar2, true);
                autoState = AutoState.WAIT_FOR_PATH5;
                break;

            case WAIT_FOR_PATH5:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_PRESHOT2;
                }
                break;

            case DRIVE_TO_PRESHOT2:
                follower.setMaxPower(0.5);
                follower.followPath(driveToPreShot, true);
                autoState = AutoState.WAIT_FOR_PATH6;
                break;

            case WAIT_FOR_PATH6:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_SHOT2;
                }
                break;

            case DRIVE_TO_SHOT2:
                follower.followPath(driveToShot2, true);
                autoState = AutoState.WAIT_FOR_PATH7;
                break;

            case WAIT_FOR_PATH7:
                if (!follower.isBusy()) {
                    autoState = AutoState.SHOOT_THE_BALLS3;
                }
                break;

            case SHOOT_THE_BALLS3:
                autoState = AutoState.WAIT_FOR_SHOOT3;
                break;

            case WAIT_FOR_SHOOT3:
                autoState = AutoState.DRIVE_TO_PRE_FINAL;
                break;

            case DRIVE_TO_PRE_FINAL:
                follower.setMaxPower(0.5);
                follower.followPath(driveToPreFinal, true);
                autoState = AutoState.WAIT_FOR_PATH8;
                break;

            case WAIT_FOR_PATH8:
                if (!follower.isBusy()) {
                    autoState = AutoState.DRIVE_TO_FINAL;
                }
                break;

            case DRIVE_TO_FINAL:
                follower.followPath(driveToFinal, true);
                autoState = AutoState.WAIT_FOR_PATH9;
                break;

            case WAIT_FOR_PATH9:
                if (!follower.isBusy()) {
                    autoState = AutoState.COMPLETE;
                }
                break;

            case COMPLETE:
                break;
        }
    }
}
