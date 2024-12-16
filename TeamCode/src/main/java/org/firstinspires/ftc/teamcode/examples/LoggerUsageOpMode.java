package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Logger;

public class LoggerUsageOpMode extends LinearOpMode {
    Logger logger;
    VoltageSensor battery;

    @Override
    public void runOpMode() throws InterruptedException {
        logger = new Logger();
        battery = hardwareMap.get(VoltageSensor.class,"Battery");
        logger.log("Robot","Robot Init");
        waitForStart();
        while(opModeIsActive()){
            // do logging
            if(battery.getVoltage() < 11){
                logger.logFault(Logger.FaultTypes.FATAL_ERROR,"Robot","Battery Critically Low! Current Voltage : " + battery.getVoltage());
            } else if (battery.getVoltage() < 13) {
                logger.logFault(Logger.FaultTypes.WARNING,"Robot","Battery Low! Current Voltage : " + battery.getVoltage());
            }
        }
    }
}
