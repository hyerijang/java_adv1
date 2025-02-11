package thread.executor;

import static util.MyLogger.log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queued = poolExecutor.getQueue().size();
            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            log("[pool=" + pool + ", activeCount=" + active + ", queuedCount=" + queued + ", completedTaskCount=" +
                completedTaskCount + "]");
        }
        else {
            log("ExecutorService is not ThreadPoolExecutor\n" + executorService);
        }
    }

    public static void printState(ExecutorService executorService, String taskName) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queued = poolExecutor.getQueue().size();
            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            log(taskName + " -> [pool=" + pool + ", activeCount=" + active + ", queuedCount=" + queued + ", completedTaskCount=" +
                completedTaskCount + "]");
        }
        else {
            log("ExecutorService is not ThreadPoolExecutor\n" + executorService);
        }
    }
}
