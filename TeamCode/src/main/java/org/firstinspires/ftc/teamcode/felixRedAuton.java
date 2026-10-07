package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "felixRedAuton")
public class felixRedAuton extends OpMode {


    // Finite state machine states.
    private enum AutoState {
        //TODO: add all states for the state machine

    }

    private Follower follower;

    //use PathChain for each path segment


    // Starting FSM state.


    @Override
    public void init() {
        //TODO: add all the hardware maps and paths for auton


        follower = Constants.createFollower(hardwareMap);


        // Reduced power for initial testing.
        follower.setMaxPower(0.5);

        buildPath();

        telemetry.addLine("Autonomous ready");
        telemetry.update();
    }

    @Override
    public void loop() {

        // Pedro must update every loop.
        follower.update();

        // Update the autonomous FSM.


        Pose currentPose = follower.getPose();

        telemetry.addData("X", currentPose.getX());

        telemetry.addData("Y", currentPose.getY());

        telemetry.addData("Heading", Math.toDegrees(currentPose.getHeading()));

        // TODO: add telementry to show auto state


        telemetry.update();
    }


    @Override
    public void stop() {
    }

    // Builds all PathChains used by this autonomous.
    private void buildPath() {

        //TODO: inset all paths here


    }
}

    // Updates the autonomous finite state machine.


