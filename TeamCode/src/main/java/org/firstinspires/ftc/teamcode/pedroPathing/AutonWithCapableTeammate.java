package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "BLUE - Collecting Teammate", group = "Autonomous")
public class AutonWithCapableTeammate extends OpMode {

    private Follower follower;
    private PathChain route;

    private final Pose startPose = pose(83.5, 135.466, 270);
    private final Pose northTransit = pose(112, 120, 270);
    private final Pose southTransit = pose(112, 31, 90);
    private final Pose southShot = pose(83.5, 31, 90);
    private final Pose bottomFlower = pose(94.33, 13.5, 270);
    private final Pose northShot = pose(83.5, 132.615, 270);
    private final Pose releasedPollenSearchEstimate = pose(83.5, 108, 270);
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
                .addPath(new BezierLine(startPose, northTransit))
                .setConstantHeadingInterpolation(startPose.getHeading())

                .addPath(new BezierLine(northTransit, southTransit))
                .setLinearHeadingInterpolation(
                        northTransit.getHeading(),
                        southTransit.getHeading()
                )

                .addPath(new BezierLine(southTransit, southShot))
                .setConstantHeadingInterpolation(southShot.getHeading())

                .addPath(new BezierLine(southShot, bottomFlower))
                .setLinearHeadingInterpolation(
                        southShot.getHeading(),
                        bottomFlower.getHeading()
                )

                .addPath(new BezierLine(bottomFlower, southTransit))
                .setLinearHeadingInterpolation(
                        bottomFlower.getHeading(),
                        southTransit.getHeading()
                )

                .addPath(new BezierLine(southTransit, northTransit))
                .setLinearHeadingInterpolation(
                        southTransit.getHeading(),
                        northTransit.getHeading()
                )

                .addPath(new BezierLine(northTransit, northShot))
                .setConstantHeadingInterpolation(northShot.getHeading())

                .addPath(new BezierLine(northShot, releasedPollenSearchEstimate))
                .setConstantHeadingInterpolation(northShot.getHeading())

                .addPath(new BezierLine(releasedPollenSearchEstimate, northTransit))
                .setConstantHeadingInterpolation(northTransit.getHeading())

                .addPath(new BezierLine(northTransit, southTransit))
                .setLinearHeadingInterpolation(
                        northTransit.getHeading(),
                        southTransit.getHeading()
                )

                .addPath(new BezierLine(southTransit, southShot))
                .setConstantHeadingInterpolation(southShot.getHeading())

                .addPath(new BezierLine(southShot, parkPose))
                .setConstantHeadingInterpolation(parkPose.getHeading())

                .build();

        telemetry.addLine("Collecting teammate route ready");
        telemetry.update();
    }

    @Override
    public void start() {
        follower.followPath(route, true);
    }

    @Override
    public void loop() {
        follower.update();

        telemetry.addData("Following", follower.isBusy());
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.update();
    }

    @Override
    public void stop() {
        if (follower != null) {
            follower.breakFollowing();
        }
    }
}