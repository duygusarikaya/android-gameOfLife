package gameoflife.util;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Duygu on 18/12/2016.
 */

public class ScheduledTaskManager {

    private static final int NUM_OF_THREADS = 1;
    private static ScheduledExecutorService scheduler = null;
    private static ScheduledTaskManager instance;

    //private static Runnable;
    private static Runnable simulation;

    private static boolean alreadyStarted = false;

    private static final long SECONDS = 1;

    private ScheduledTaskManager() {}
    public static ScheduledTaskManager getInstance(Context context) {
        if (instance == null) {
            instance = new ScheduledTaskManager();
        }
        return instance;
    }

    public boolean start() {
        if (!alreadyStarted) {
            scheduler  = Executors.newScheduledThreadPool(NUM_OF_THREADS);
            startSimulationTask();
            alreadyStarted = true;
            Log.d("start", "Tasks are scheduled.");
            return true;
        } else {
            Log.d("start", "No action. Tasks are already scheduled. Stop them first.");
        }
        return false;
    }

    public boolean stop() {
        if (alreadyStarted) {
            if (scheduler != null) {
                scheduler.shutdown();
                Log.d("stop", "Tasks are scheduled to stop.");
                alreadyStarted = false;
                return true;
            }
        } else {
            Log.d("stop", "No action. No scheduled tasks. Start them first.");
        }
        return false;
    }

    public boolean stopNow() {
        if (alreadyStarted) {
            if (scheduler != null) {
                scheduler.shutdownNow();
                Log.d("stopNow", "Tasks are forcefully stopped.");
                alreadyStarted = false;
                return true;
            }
        } else {
            Log.d("stopNow", "No action. No scheduled tasks. Start them first.");
        }
        return false;
    }

    private static void startSimulationTask() {
        simulation = new Simulation();
        scheduler.scheduleWithFixedDelay(simulation,0, SECONDS , TimeUnit.SECONDS);
        Log.d("startSimulationTask", "Simulation thread started.");
    }
}
