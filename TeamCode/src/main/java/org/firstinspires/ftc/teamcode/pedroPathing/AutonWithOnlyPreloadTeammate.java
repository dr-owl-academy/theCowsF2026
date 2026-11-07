package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "67mrbeastlebronistheGOAAAAT")
public class AutonWithOnlyPreloadTeammate extends OpMode {

    private enum AutoState {
        SOUTH_PRELOAD,
        BOTTOM_FLOWER,
        SOUTH_SECOND_BATCH,
        RIGHT_FLOWER,
        NORTH_FIRST_BATCH,
        GARDEN,
        NORTH_SECOND_BATCH,
        PARK,
        COMPLETE
    }

    private Follower follower;
    private AutoState autoState = AutoState.COMPLETE;

    private final Pose startPose = pose(96, 133, 270);
    private final Pose northTransit = pose(112, 120, 270);
    private final Pose southTransit = pose(112, 31, 90);
    private final Pose southShot = pose(83.5, 31, 90);
    private final Pose bottomFlower = pose(94.33, 19.5, 270);
    private final Pose rightApproach = pose(111.28, 94.26, 0);
    private final Pose rightFlower = pose(125.51, 94.35, 0);
    private final Pose northShot = pose(83.5, 132.615, 270);
    private final Pose gardenPickupEstimate = pose(134, 132, 90);
    private final Pose gardenExit = pose(112, 120, 90);
    private final Pose parkPose = pose(130, 35, 90);

    private PathChain toSouthPreload;
    private PathChain toBottomFlower;
    private PathChain backToSouth;
    private PathChain toRightFlower;
    private PathChain toNorth;
    private PathChain toGarden;
    private PathChain backToNorth;
    private PathChain toPark;

    private static Pose pose(double x, double y, double degrees) {
        return new Pose(x, y, Math.toRadians(degrees));
    }

    private PathChain path(Pose... points) {
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

        toSouthPreload = path(
                startPose,
                northTransit,
                southTransit,
                southShot
        );

        toBottomFlower = path(
                southShot,
                bottomFlower
        );

        backToSouth = path(
                bottomFlower,
                southShot
        );

        toRightFlower = path(
                southShot,
                southTransit,
                rightApproach,
                rightFlower
        );

        toNorth = path(
                rightFlower,
                rightApproach,
                northTransit,
                northShot
        );

        toGarden = path(
                northShot,
                northTransit,
                gardenPickupEstimate
        );

        backToNorth = path(
                gardenPickupEstimate,
                gardenExit,
                northShot
        );

        toPark = path(
                northShot,
                northTransit,
                southTransit,
                parkPose
        );
    }

    @Override
    public void start() {
        drive(toSouthPreload, AutoState.SOUTH_PRELOAD);
    }

    @Override
    public void loop() {
        follower.update();

        if (follower.isBusy()) {
            return;
        }

        switch (autoState) {
            case SOUTH_PRELOAD:
                drive(toBottomFlower, AutoState.BOTTOM_FLOWER);
                break;

            case BOTTOM_FLOWER:
                drive(backToSouth, AutoState.SOUTH_SECOND_BATCH);
                break;

            case SOUTH_SECOND_BATCH:
                drive(toRightFlower, AutoState.RIGHT_FLOWER);
                break;

            case RIGHT_FLOWER:
                drive(toNorth, AutoState.NORTH_FIRST_BATCH);
                break;

            case NORTH_FIRST_BATCH:
                drive(toGarden, AutoState.GARDEN);
                break;

            case GARDEN:
                drive(backToNorth, AutoState.NORTH_SECOND_BATCH);
                break;

            case NORTH_SECOND_BATCH:
                drive(toPark, AutoState.PARK);
                break;

            case PARK:
                follower.breakFollowing();
                autoState = AutoState.COMPLETE;
                break;

            case COMPLETE:
                break;
        }
    }

    private void drive(PathChain nextPath, AutoState nextState) {
        autoState = nextState;
        follower.followPath(nextPath, true);
    }

    @Override
    public void stop() {
        autoState = AutoState.COMPLETE;

        if (follower != null) {
            follower.breakFollowing();
        }
    }
}