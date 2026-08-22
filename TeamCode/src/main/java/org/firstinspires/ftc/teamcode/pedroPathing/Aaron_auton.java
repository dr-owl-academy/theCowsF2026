package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Aaron_auton")
public class Aaron_auton extends OpMode {

    private DcMotor testMotor;

    private enum AutoState {
        START_TURN_TO_180,
        WAIT_FOR_TURN_TO_180,
        START_DRIVE_TO_YELLOW_TARGET,
        WAIT_FOR_DRIVE_TO_YELLOW_TARGET,
        START_DRIVE_TO_GREEN_TARGET,
        WAIT_FOR_DRIVE_TO_GREEN_TARGET,
        COMPLETE
    }

    private Follower follower;

    // Define separate path chains for each segment
    private PathChain driveToYellowTarget;
    private PathChain driveToGreenTarget;

    private AutoState autoState = AutoState.START_TURN_TO_180;

    private static final Pose START_POSE = new Pose(72, 72, Math.toRadians(90));
    private static final Pose DRIVE_START_POSE = new Pose(72, 72, Math.toRadians(180));
    private static final Pose YELLOW_TARGET_POSE = new Pose(24, 72, Math.toRadians(180));
    private static final Pose GREEN_TARGET_POSE = new Pose(72, 24, Math.toRadians(-90));

    @Override
    public void init() {
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
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

        if (autoState == AutoState.COMPLETE) {
            telemetry.addLine("Autonomous complete");
        }

        telemetry.update();
    }

    @Override
    public void stop() {
    }

    private void buildPath() {
        // Path from DRIVE_START_POSE to YELLOW_TARGET_POSE
        driveToYellowTarget = follower.pathBuilder()
                .addPath(new BezierLine(DRIVE_START_POSE, YELLOW_TARGET_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        // Path from YELLOW_TARGET_POSE to GREEN_TARGET_POSE
        driveToGreenTarget = follower.pathBuilder()
                .addPath(new BezierLine(YELLOW_TARGET_POSE, GREEN_TARGET_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    private void autonomousPathUpdate() {
        switch (autoState) {
            case START_TURN_TO_180:
                follower.turnTo(Math.toRadians(180));
                autoState = AutoState.WAIT_FOR_TURN_TO_180;
                break;


            case WAIT_FOR_TURN_TO_180:
                if (!follower.isBusy()) {
                    autoState = AutoState.START_DRIVE_TO_YELLOW_TARGET;
                    testMotor.setPower(1);
                }
                break;


            case START_DRIVE_TO_YELLOW_TARGET:
                follower.followPath(driveToYellowTarget, true);
                autoState = AutoState.WAIT_FOR_DRIVE_TO_YELLOW_TARGET;
                break;


            case WAIT_FOR_DRIVE_TO_YELLOW_TARGET:
                if (!follower.isBusy()) {
                    autoState = AutoState.START_DRIVE_TO_GREEN_TARGET;

                }
                break;


            case START_DRIVE_TO_GREEN_TARGET:
                follower.followPath(driveToGreenTarget, true);
                autoState = AutoState.WAIT_FOR_DRIVE_TO_GREEN_TARGET;
                break;

            case WAIT_FOR_DRIVE_TO_GREEN_TARGET:
                if (!follower.isBusy()) {
                    autoState = AutoState.COMPLETE;
                    testMotor.setPower(0);
                }
                break;

            case COMPLETE:
                break;
        }
    }
}