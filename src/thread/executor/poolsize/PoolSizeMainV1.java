package thread.executor.poolsize;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import thread.executor.RunnableTask;
import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV1 {

    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2); // NOTE : 큐 사이즈 : 2
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3")); // NOTE : task3 -> [pool=2, activeCount=1, queuedCount=1, completedTaskCount=0]
        printState(es, "task3");

        es.execute(new RunnableTask("task4")); // NOTE :  task4 -> [pool=2, activeCount=2, queuedCount=2, completedTaskCount=0]
        printState(es, "task4");

        // 큐가 다 찼음

        // NOTE : 큐가 다 차서 더는 넣을 수 없을 때, maxPoolSize까지 늘어난다!
        es.execute(new RunnableTask("task5")); // IMPORT :  task5 -> [pool=3, activeCount=3, queuedCount=2, completedTaskCount=0]
        printState(es, "task5");

        es.execute(new RunnableTask("task6")); // NOTE :  task6 -> [pool=4, activeCount=4, queuedCount=2, completedTaskCount=0]
        printState(es, "task6");

        // NOTE : 큐도 다 차고 + maxPoolSize까지 다 찼음 -> RejectedExecutionException
        try {

            es.execute(new RunnableTask("task7")); // IMPORT :  Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task thread.executor.RunnableTask@4d95d2a2 rejected from java.util.concurrent.ThreadPoolExecutor@7ca48474[Running, pool size = 4, active threads = 4, queued tasks = 2, completed tasks = 0]
            printState(es, "task7");
        } catch (Exception e) {
            log("task 7 실행 거절 예외 발생 : " + e);
        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);


        sleep(3000);
        log("==  keepAliveTime (maximumPoolSize 대기시간) 초과 ==");
        printState(es);

        es.close();
        log("== shutdown 완료 ==");
        printState(es);
    }
}
