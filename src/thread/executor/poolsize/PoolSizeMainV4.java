package thread.executor.poolsize;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import thread.executor.RunnableTask;

public class PoolSizeMainV4 {

//    static final int TASK_SIZE = 1100; // 1.일반
//    static final int TASK_SIZE = 1200; // 1.긴급
    static final int TASK_SIZE = 1201; // 3.거절


    public static void main(String[] args) {

        ExecutorService es = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        printState(es);

        long startMs = System.currentTimeMillis();

        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                es.execute(new RunnableTask(taskName));
                printState(es, taskName);
            } catch (RejectedExecutionException e) {
                log("작업 추가 실패 : " + taskName);
            }
        }

        es.close();
        long endMs = System.currentTimeMillis();
        log("소요시간 : " + (endMs - startMs));
    }
}