package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "BLUEAUTON", group = "Autonomous")
public class BLUEAUTON extends OpMode {

    private Follower follower;
    private PathChain route;

    private final Pose startPose = pose(83.5, 132.615, 270);
    private final Pose rightFlower = pose(128, 94.33, 0);
    private final Pose exitPose = pose(112, 31, 90);
    private final Pose secondCell = pose(83.5, 31, 90);
    private final Pose bottomFlower = pose(94.33, 13.5, 270);
    private final Pose finalShot = pose(83.5, 21.5, 90);
    private final Pose parkPose = pose(130, 35, 90);

    private static Pose pose(double x, double y, double degrees) {
        return new Pose(x, y, Math.toRadians(degrees));
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.setMaxPower(0.3);

        route = follower.pathBuilder()
                .addPath(new BezierLine(startPose, rightFlower))
                .setLinearHeadingInterpolation(
                        startPose.getHeading(),
                        rightFlower.getHeading()
                )
                .addPath(new BezierLine(rightFlower, exitPose))
                .setLinearHeadingInterpolation(
                        rightFlower.getHeading(),
                        exitPose.getHeading()
                )
                .addPath(new BezierLine(exitPose, secondCell))
                .setConstantHeadingInterpolation(secondCell.getHeading())
                .addPath(new BezierLine(secondCell, bottomFlower))
                .setLinearHeadingInterpolation(
                        secondCell.getHeading(),
                        bottomFlower.getHeading()
                )
                .addPath(new BezierLine(bottomFlower, finalShot))
                .setLinearHeadingInterpolation(
                        bottomFlower.getHeading(),
                        finalShot.getHeading()
                )
                .addPath(new BezierLine(finalShot, parkPose))
                .setConstantHeadingInterpolation(parkPose.getHeading())
                .build();

        telemetry.addLine("BLUEAUTON ready");
        telemetry.update();
    }

    @Override
    public void start() {
        follower.followPath(route, true);
    }

    @Override
    public void loop() {
        follower.update();

        telemetry.addData("Following path", follower.isBusy());
        telemetry.update();
    }

    @Override
    public void stop() {
        if (follower != null) {
            follower.breakFollowing();
        }
    }
}