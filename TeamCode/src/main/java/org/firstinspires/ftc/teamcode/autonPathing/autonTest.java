package org.firstinspires.ftc.teamcode.autonPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name="autonTest")
public class autonTest extends OpMode{
    private enum AutoState{
        GOFORWARD,
        TURNRIGHT,
        STOP
    }
    private Follower follower;
    private PathChain GOFORWARD;
    private PathChain TURNRIGHT;
    private PathChain STOP;
    private static final Pose START_POSE = new Pose(56, 8, Math.toRadians(90));
    private static final Pose SECOND_POSE = new Pose(56, 36, Math.toRadians(180));
    private static final Pose FINAL_POSE = new Pose(20.421, 37.480, Math.toRadians(180));

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setMaxPower(1);
        buildPath();
        telemetry.addLine("Auton Ready :)))))");

    }

    @Override
    public void loop() {

    }

    @Override
    public void stop(){

    }
}
