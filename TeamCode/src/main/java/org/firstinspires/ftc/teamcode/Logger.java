package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * simple wrapper class for SimpleLogger with extra features.
 */
@Disabled
public class Logger extends LinearOpMode {
    public enum FaultTypes{
        INFO("INFO : "),
        WARNING("WARNING : "),
        ERROR("ERROR : "),
        FATAL_ERROR("FATAL_ERROR : ");
        private final String value;

        // Constructor to assign string values
        FaultTypes(String value) {
            this.value = value;
        }

        // Getter method to retrieve string value
        public String getValue() {
            return value;
        }
    }
    static SimpleLogger simpleLogger;
    /**
     * creates a new Logger instance using a simple logger instance.
     */
    public Logger(SimpleLogger simpleLogger){
        Logger.simpleLogger = simpleLogger; // inject the simple logger
    }
    /**
     * Creates a new Logger instance and a new Simplelogger instance at the same time.
     */
    public Logger(){
        Logger.simpleLogger = new SimpleLogger(); // inject the simple logger
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // No, Don't even think about it.
    }

    public void log(String path,Object value){
        simpleLogger.log(path,value);
    }
    public void logFault(FaultTypes faultType, String path,Object fault){
        log(path,faultType.getValue()+fault); // we just have a log that shows a fault type ex:`ERROR : err`
    }
    public void closeLogs(){simpleLogger.closeLogs();}
    public static void printLogFile(String path) throws IOException, IOException {
        File logFile = new File(path+simpleLogger.getBaseLogDir());
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
    /**
     * This changes the <strong>BASE</strong> hardware directory, it is recommended to not change this, but it might be necessary when logging into a USB, or a custom hardware path The default BaseHardwareDirectory is <code>/sdcard/FIRST/java/src/Datalogs/</code> It is recommended to stay in this format. Please <strong>DO NOT CHANGE THIS</strong> during runtime, change the path before logging any instances ex:<code>logger.log("Robot","Robot Init");</code>
     */
    public void setBaseHardwareDirectory(String baseHardwareDirectory) throws RuntimeException{
        if(opModeIsActive()){
            // so none of you bums try changing this while code running
            throw new RuntimeException("Set the Base Hardware Directory to something else while opModeIsActive, do this before opmode is active to avoid bugs.");
        }else{
            simpleLogger.hardwareLogDir = baseHardwareDirectory;
        }
    }
    public String getBaseHardwareDirectory(){return simpleLogger.hardwareLogDir;}
    /**
     * This returns <code>SimpleLogger</code> which could be treated as a lower level definition of the <code>Logger</code> class
     */
    public SimpleLogger getSimpleLogger(){return simpleLogger;} // so user can drop to lower level library
}