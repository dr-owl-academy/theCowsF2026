
package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "autonRedPath4")
public class autonRedPath4 extends OpMode {

    private enum AutoState {
        SHOOT_THE_BALLS,
        WAIT_FOR_SHOT,
        PATH_ONE,
        WAIT_FOR_PATH1,
        SHOT1,
        WAIT_FOR_SHOT1,
        SHOOT_BALLS1,
        WAIT_FOR_SHOOT_BALLS1,
        PATH2,
        WAIT_FOR_PATH2,
        PATH3,
        WAIT_FOR_PATH3,
        PATH4,
        WAIT_FOR_PATH4,
        POLLEN,
        WAIT_FOR_POLLEN,
        INTAKE_POLLEN,
        WAIT_FOR_INTAKE,
        SHOT2,
        WAIT_FOR_SHOT2,
        SHOOT_BALLS2,
        WAIT_FOR_SHOOT_BALLS2,
        PRE_FINAL,
        WAIT_FOR_PRE_FINAL,
        FINAL,
        WAIT_FOR_FINAL,
        COMPLETE
    }

    private Follower follower;

    private PathChain driveToPathOne;
    private PathChain driveToShot1;
    private PathChain driveToPath2;
    private PathChain driveToPath3;
    private PathChain driveToPath4;
    private PathChain driveToPollen;
    private PathChain driveToPreFinal;
    private PathChain driveToFinal;

    private AutoState autoState = AutoState.SHOOT_THE_BALLS;

    private static final Pose START_POSE =
            new Pose(24, 132.56376334986388, Math.toRadians(270));

    private static final Pose PRESHOT_POSE1 =
            new Pose(22.83, 114.34, Math.toRadians(270));

    private static final Pose SHOT1 =
            new Pose(60.25, 114.79, Math.toRadians(270));

    private static final Pose POSE4 =
            new Pose(16.06, 101.22, Math.toRadians(198));

    private static final Pose POSE5 =
            new Pose(18.65, 46.16, Math.toRadians(272));

    private static final Pose POLLENINTAKEPOSE =
            new Pose(10.32, 10.62, Math.toRadians(257));

    private static final Pose SHOTPOSE2 =
            new Pose(62.03, 23.52, Math.toRadians(90));

    private static final Pose ALMOST_FINAL_POST =
            new Pose(23.15, 35.37, Math.toRadians(164));

    private static final Pose FINAL_POSE =
            new Pose(10.39, 118.16, Math.toRadians(102));


    @Override
    public void init() {

        follower = Constants.createFollower(hardwareMap);

        follower.setStartingPose(START_POSE);
        follower.setMaxPower(0.5);

        buildPaths();

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
        telemetry.addData("Heading",
                Math.toDegrees(currentPose.getHeading()));

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


    private void buildPaths() {

        driveToPathOne = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, PRESHOT_POSE1))
                .setLinearHeadingInterpolation(
                        START_POSE.getHeading(),
                        PRESHOT_POSE1.getHeading())
                .build();

        driveToShot1 = follower.pathBuilder()
                .addPath(new BezierLine(PRESHOT_POSE1, SHOT1))
                .setLinearHeadingInterpolation(
                        PRESHOT_POSE1.getHeading(),
                        SHOT1.getHeading())
                .build();

        driveToPath2 = follower.pathBuilder()
                .addPath(new BezierLine(SHOT1, POSE4))
                .setLinearHeadingInterpolation(
                        SHOT1.getHeading(),
                        POSE4.getHeading())
                .build();

        driveToPath3 = follower.pathBuilder()
                .addPath(new BezierLine(POSE4, POSE5))
                .setLinearHeadingInterpolation(
                        POSE4.getHeading(),
                        POSE5.getHeading())
                .build();

        driveToPath4 = follower.pathBuilder()
                .addPath(new BezierLine(POSE5, POLLENINTAKEPOSE))
                .setLinearHeadingInterpolation(
                        POSE5.getHeading(),
                        POLLENINTAKEPOSE.getHeading())
                .build();

        driveToPollen = follower.pathBuilder()
                .addPath(new BezierLine(POLLENINTAKEPOSE, SHOTPOSE2))
                .setLinearHeadingInterpolation(
                        POLLENINTAKEPOSE.getHeading(),
                        SHOTPOSE2.getHeading())
                .build();

        driveToPreFinal = follower.pathBuilder()
                .addPath(new BezierLine(SHOTPOSE2, ALMOST_FINAL_POST))
                .setLinearHeadingInterpolation(
                        SHOTPOSE2.getHeading(),
                        ALMOST_FINAL_POST.getHeading())
                .build();

        driveToFinal = follower.pathBuilder()
                .addPath(new BezierLine(ALMOST_FINAL_POST, FINAL_POSE))
                .setLinearHeadingInterpolation(
                        ALMOST_FINAL_POST.getHeading(),
                        FINAL_POSE.getHeading())
                .build();
    }


    private void autonomousPathUpdate() {

        switch (autoState) {

            case COMPLETE:
                break;
        }
    }
}
