package org.firstinspires.ftc.teamcode;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * simple wrapper class for SimpleLogger with extra features.
 */
public class Logger {
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
    public SimpleLogger getSimpleLogger(){return simpleLogger;} // so user can drop to lower level library

}
