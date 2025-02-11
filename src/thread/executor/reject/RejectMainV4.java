package thread.executor.reject;

import static java.util.concurrent.TimeUnit.SECONDS;
import static util.MyLogger.log;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import thread.executor.RunnableTask;

public class RejectMainV4 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(1, 1, 0, SECONDS,
                new SynchronousQueue<>(), new MyRejectedExecutionHandler());

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

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
        static AtomicInteger count = new AtomicInteger(0);
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int i = count.incrementAndGet();
            log("[경고] 거절된 누적 작업 수: " + i);
        }
    }
}
