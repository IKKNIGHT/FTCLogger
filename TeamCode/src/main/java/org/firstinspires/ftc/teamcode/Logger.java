package org.firstinspires.ftc.teamcode;


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
    SimpleLogger simpleLogger;
    public Logger(SimpleLogger simpleLogger){
        this.simpleLogger = simpleLogger; // inject the simple logger
    }
    public void log(String path,Object value){
        simpleLogger.log(path,value);
    }
    public void logFault(FaultTypes faultType, String path,Object fault){
        log(path,faultType.getValue()+fault); // we just have a log that shows a fault type ex:`ERROR : err`
    }
    public void closeLogs(){simpleLogger.closeLogs();}
    public SimpleLogger getSimpleLogger(){return simpleLogger;} // so user can drop to lower level library

}
