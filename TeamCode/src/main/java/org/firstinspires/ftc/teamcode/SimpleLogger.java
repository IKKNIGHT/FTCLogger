// Copyright 2024 Patrick R. Michaud

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleLogger {
    // Map to manage writers for multiple hierarchical paths
    private final Map<String, BufferedWriter> writers = new HashMap<>();
    // Timer to track elapsed time since logging started
    private final ElapsedTime logTime = new ElapsedTime();
    // Base directory for logs
    private final String baseLogDir = "/sdcard/FIRST/java/src/Datalogs/";

    /**
     * Constructor sets up the OpMode lifecycle listener.
     */
    public SimpleLogger() {
        // Register a listener to manage file closure during the OpMode lifecycle
        OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity())
                .registerListener(new OpModeNotifications());
    }

    /**
     * Logs a value at a specific hierarchical path.
     * This creates or reuses a file for the given path.
     *
     * @param path Hierarchical path to the value (e.g., "Subsystems/Arm")
     * @param value The value to log at the specified path
     */
    public void log(String path, Object value) {
        BufferedWriter writer = writers.computeIfAbsent(path, this::createWriter);

        try {
            // Write timestamp and value to the file
            writer.write(String.format("%.2f, %s\n", logTime.milliseconds(), value));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a BufferedWriter for a given path, ensuring directories exist.
     *
     * @param path The hierarchical path for the log file
     * @return A BufferedWriter for the specified path
     */
    private BufferedWriter createWriter(String path) {
        try {
            // Replace '/' in path with '_' for file naming
            String sanitizedPath = path.replace("/", "_");
            String fullPath = baseLogDir + sanitizedPath + ".txt";
            File file = new File(fullPath);

            // Create directories if they do not exist
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // Initialize and return a new BufferedWriter
            return new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create log file for path: " + path, e);
        }
    }

    /**
     * Closes all log files and releases system resources.
     */
    public void closeLogs() {
        for (Map.Entry<String, BufferedWriter> entry : writers.entrySet()) {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writers.clear();
    }

    /**
     * Listener for OpMode lifecycle events to ensure log files are properly closed.
     */
    private class OpModeNotifications implements OpModeManagerNotifier.Notifications {
        /**
         * Called when the OpMode stops, ensuring all log files are closed.
         *
         * @param opMode The current OpMode
         */
        @Override
        public void onOpModePostStop(OpMode opMode) {
            closeLogs();

            // Unregister the listener to clean up resources
            OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity())
                    .unregisterListener(this);
        }

        @Override
        public void onOpModePreInit(OpMode opMode) {}

        @Override
        public void onOpModePreStart(OpMode opMode) {}
    }
}
