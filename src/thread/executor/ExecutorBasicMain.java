package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorBasicMain {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        log(" == 초기 상태 == ");
        printState(es); // [pool=0, activeCount=0, queuedCount=0, completedTaskCount=0]


        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));
        log(" == 작업 수행 중 == ");
        printState(es); // [pool=2, activeCount=2, queuedCount=2, completedTaskCount=0]

        sleep(3000);
        log(" == 작업 수행 완료 == ");
        printState(es); // [pool=2, activeCount=0, queuedCount=0, completedTaskCount=4]

        es.close(); // import : 중요!
        log("== shutdown 완료 ==");
        printState(es); // [pool=0, activeCount=0, queuedCount=0, completedTaskCount=4]
    }
}
