package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "BLUE - Partner State Machine", group = "Autonomous")
public class AutonWithCapableTeammate extends OpMode {

    private Follower follower;

    private final Pose startPose = pose(83.5, 135.466, 270);
    private final Pose northTransit = pose(112, 120, 270);
    private final Pose southTransit = pose(112, 31, 90);
    private final Pose southShot = pose(83.5, 31, 90);
    private final Pose bottomFlower = pose(94.33, 13.5, 270);
    private final Pose northShot = pose(83.5, 132.615, 270);
    private final Pose releasedPollenSearchEstimate = pose(83.5, 108, 270);
    private final Pose parkPose = pose(130, 35, 90);

    private PathChain firstSouthPath;
    private PathChain flowerPath;
    private PathChain northPath;
    private PathChain searchPath;
    private PathChain returnSouthPath;
    private PathChain parkPath;

    private enum State {
        FIRST_SOUTH,
        BOTTOM_FLOWER,
        NORTH_SHOT,
        POLLEN_SEARCH,
        RETURN_SOUTH,
        PARK,
        DONE
    }

    private State state = State.DONE;

    private static Pose pose(double x, double y, double degrees) {
        return new Pose(x, y, Math.toRadians(degrees));
    }

    private PathChain buildPath(Pose... points) {
        PathBuilder builder = follower.pathBuilder();

        for (int i = 0; i < points.length - 1; i++) {
            builder.addPath(new BezierLine(points[i], points[i + 1]))
                    .setLinearHeadingInterpolation(
                            points[i].getHeading(),
                            points[i + 1].getHeading()
                    );
        }

        return builder.build();
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.setMaxPower(0.6);

        firstSouthPath = buildPath(
                startPose,
                northTransit,
                southTransit,
                southShot
        );

        flowerPath = buildPath(
                southShot,
                bottomFlower
        );

        northPath = buildPath(
                bottomFlower,
                southTransit,
                northTransit,
                northShot
        );

        searchPath = buildPath(
                northShot,
                releasedPollenSearchEstimate
        );

        returnSouthPath = buildPath(
                releasedPollenSearchEstimate,
                northTransit,
                southTransit,
                southShot
        );

        parkPath = buildPath(
                southShot,
                parkPose
        );
    }

    @Override
    public void start() {
        drive(firstSouthPath, State.FIRST_SOUTH);
    }

    @Override
    public void loop() {
        follower.update();

        if (follower.isBusy()) {
            return;
        }

        switch (state) {
            case FIRST_SOUTH:
                drive(flowerPath, State.BOTTOM_FLOWER);
                break;

            case BOTTOM_FLOWER:
                drive(northPath, State.NORTH_SHOT);
                break;

            case NORTH_SHOT:
                drive(searchPath, State.POLLEN_SEARCH);
                break;

            case POLLEN_SEARCH:
                drive(returnSouthPath, State.RETURN_SOUTH);
                break;

            case RETURN_SOUTH:
                drive(parkPath, State.PARK);
                break;

            case PARK:
                follower.breakFollowing();
                state = State.DONE;
                break;

            case DONE:
                break;
        }
    }

    private void drive(PathChain path, State nextState) {
        state = nextState;
        follower.followPath(path, true);
    }

    @Override
    public void stop() {
        state = State.DONE;

        if (follower != null) {
            follower.breakFollowing();
        }
    }
}