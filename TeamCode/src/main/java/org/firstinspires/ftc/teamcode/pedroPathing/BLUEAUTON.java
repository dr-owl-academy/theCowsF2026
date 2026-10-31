package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled // Remove after hardware configuration and tuning.
@Autonomous(name = "One Man Team Auton")
public class BLUEAUTON extends OpMode {
    private Follower follower;
    private final ElapsedTime total = new ElapsedTime();
    private final ElapsedTime timer = new ElapsedTime();

    // Starting heading provisionally uses 270; your file also contained 90.
    private final Pose[] points = {
            pose(83.5, 132.615, 270), // 0: preload
            pose(128, 94.33, 0),     // 1: right flower
            pose(83.5, 31, 90),      // 2: second cell
            pose(94.33, 13.5, 270),
            pose(83.5, 21.5, 90),
            pose(130, 35, 90)
    };

    private final double[] pauses = {3, 2, 2, 2, 3, 0};
    private final PathChain[] paths = new PathChain[6];
    private int step;
    private boolean moving;
    private boolean done;
    private String status = "Drive-only draft";

    private static Pose pose(double x, double y, double degrees) {
        return new Pose(x, y, Math.toRadians(degrees));
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(points[0]);
        follower.setMaxPower(0.3);

        for (int i = 1; i < points.length; i++) {
            paths[i] = follower.pathBuilder()
                    .addPath(new BezierLine(points[i - 1], points[i]))
                    .setLinearHeadingInterpolation(
                            points[i - 1].getHeading(), points[i].getHeading())
                    .build();
        }

        Pose exit = pose(112, 31, 90);
        paths[2] = follower.pathBuilder()
                .addPath(new BezierLine(points[1], exit))
                .setLinearHeadingInterpolation(points[1].getHeading(), exit.getHeading())
                .addPath(new BezierLine(exit, points[2]))
                .setConstantHeadingInterpolation(points[2].getHeading())
                .build();

        initMechanisms();
        telemetry.addLine("Drive-only draft: verify configuration before running.");
        telemetry.update();
    }

    @Override
    public void start() {
        total.reset();
        timer.reset();
        step = 0;
        moving = false;
        done = false;
        follower.holdPoint(points[0]);
        beginAction();
    }

    @Override
    public void loop() {
        if (!done) {
            if (total.seconds() >= 29) {
                finish("Time limit");
            } else {
                follower.update();
                updateMechanisms();

                if (total.seconds() >= 26 && step < 5) {
                    if (step == 4 && near(points[4])) {
                        driveTo(5);
                    } else {
                        finish("Too late for planned parking route; stopped");
                    }
                } else if (moving) {
                    if (!follower.isBusy()) {
                        if (!near(points[step])) {
                            finish("Path ended outside target tolerance");
                        } else {
                            moving = false;
                            timer.reset();
                            beginAction();
                            if (step == 5) finish("Parked");
                        }
                    } else if (timer.seconds() >= 10) {
                        finish("Movement timeout");
                    }
                } else if (timer.seconds() >= pauses[step]) {
                    driveTo(step + 1);
                }
            }
        }

        telemetry.addData("Step", step);
        telemetry.addData("Moving", moving);
        telemetry.addData("Seconds", total.seconds());
        telemetry.addData("Status", status);
        telemetry.update();
    }

    private void driveTo(int next) {
        stopMechanisms();
        if (next >= points.length) {
            finish("Finished");
            return;
        }
        step = next;
        moving = true;
        timer.reset();
        follower.followPath(paths[step], true);
    }

    private boolean near(Pose target) {
        Pose current = follower.getPose();
        double angle = target.getHeading() - current.getHeading();
        angle = Math.atan2(Math.sin(angle), Math.cos(angle));
        return Math.hypot(target.getX() - current.getX(),
                target.getY() - current.getY()) <= 1.5
                && Math.abs(angle) <= Math.toRadians(5);
    }

    private void beginAction() {
        switch (step) {
            case 0: startShooting(3); break;
            case 1:
            case 3: startIntake(); break;
            case 2:
            case 4: startShooting(4); break;
            default: stopMechanisms(); break;
        }
    }

    private void finish(String reason) {
        done = true;
        status = reason;
        if (follower != null) follower.breakFollowing();
        stopMechanisms();
    }

    @Override
    public void stop() {
        finish("Stopped");
    }

    // Add your real mechanism code here. These currently do nothing.
    // Keep these methods nonblocking: no sleeps or waiting loops.
    private void initMechanisms() {}
    private void updateMechanisms() {}
    private void startShooting(int count) {}
    private void startIntake() {}
    private void stopMechanisms() {}
}