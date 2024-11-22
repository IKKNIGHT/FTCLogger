package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="TeleOp", group = "Real")
public class ManualTestingOpmode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot(this);
        robot.configureSubsystemTestingTeleOpBindings();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            robot.run();
        }
    }
}