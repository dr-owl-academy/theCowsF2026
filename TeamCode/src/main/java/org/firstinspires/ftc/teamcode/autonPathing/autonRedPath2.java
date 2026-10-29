
package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
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

    private PathChain driveToFlower1;
    private PathChain driveToFlower2;
    private PathChain driveToShot1;
    private PathChain driveToNectar1;
    private PathChain driveToNectar2;
    private PathChain drivetoPreShot;
    private PathChain driveToShot2;
    private PathChain drivetoPreFinal;
    private PathChain drivetoFinal;


    private AutoState autoState = AutoState.SHOOT_THE_BALLS;

    private static final Pose START_POSE =
            new Pose(48, 9, Math.toRadians(90));
    private static final Pose FLOWER_POSE1 =
            new Pose(22.48, 46.69, Math.toRadians(180));
    private static final Pose FLOWER_POSE2 =
            new Pose(10.46,47.29,Math.toRadians(180));
    private static final Pose SHOT_POSE1 =
            new Pose(45.15,126.71,Math.toRadians(270));
    private static final Pose PRENECTAR_INTAKE_POSE =
            new Pose(9.13,126.56,Math.toRadians(270));
    private static final Pose NECTAR_INTAKE_POSE =
            new Pose(8.9,103.8,Math.toRadians(270));
    private static final Pose PRE_SHOT2 =
            new Pose(38.65,18.31,Math.toRadians(90));
    private static final Pose SHOT_POSE2 =
            new Pose(56.12,17.98,Math.toRadians(90));
    private static final Pose ALMOST_FINAL_POST =
            new Pose(56.12,17.98,Math.toRadians(90));
    private static final Pose FINAL_POSE =
            new Pose(56.12,17.98,Math.toRadians(90));






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
        driveToFlower1 = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE,FLOWER_POSE1))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();
        driveToFlower2 = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE1,FLOWER_POSE2))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        driveToShot1 = follower.pathBuilder()
                .addPath(new BezierLine(FLOWER_POSE2,SHOT_POSE1))
                .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(270))
                .build();
        driveToNectar1= follower.pathBuilder()
                .addPath(new BezierLine(SHOT_POSE1,PRENECTAR_INTAKE_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();
        driveToNectar2 = follower.pathBuilder()
                .addPath(new BezierLine(PRENECTAR_INTAKE_POSE,NECTAR_INTAKE_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();
        drivetoPreShot = follower.pathBuilder()
                .addPath(new BezierLine(NECTAR_INTAKE_POSE,PRE_SHOT2))
                .setLinearHeadingInterpolation(Math.toRadians(270),Math.toRadians(90))
                .build();
        driveToShot2 = follower.pathBuilder()
            .addPath(new BezierLine(PRE_SHOT2,SHOT_POSE2))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
        drivetoPreFinal = follower.pathBuilder()
                .addPath(new BezierLine(SHOT_POSE2,ALMOST_FINAL_POST))
                .setConstantHeadingInterpolation(Math.toRadians(167))
                .build();
        drivetoFinal = follower.pathBuilder()
                .addPath(new BezierLine(ALMOST_FINAL_POST,FINAL_POSE))
                .setConstantHeadingInterpolation(Math.toRadians(167))
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
