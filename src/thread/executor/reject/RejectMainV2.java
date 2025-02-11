package thread.executor.reject;

import static java.util.concurrent.TimeUnit.SECONDS;
import static util.MyLogger.log;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import thread.executor.RunnableTask;

public class RejectMainV2 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(1, 1, 0, SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());

        executor.submit(new RunnableTask("task1"));
        try {
            executor.submit(new RunnableTask("task2"));
        }
        catch (RejectedExecutionException e) {
            log("요청 초과");
            log(e);
        }

        executor.close();
    }
}
