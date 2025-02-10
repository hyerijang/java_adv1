package thread.executor;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import java.util.concurrent.ExecutorService;

public class ExecutorShutdownMain {

    public static void main(String[] args) {
        ExecutorService es = newFixedThreadPool(3);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000)); // 100초

        printState(es);
        log("== shutdown 시작 ==");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료 ==");
        printState(es);
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업을 받지 않는다. 처리 중이거나 큐에 이미 대기중인 작업은 처리한다. 이후에 풀의 스레드를 종료한다.

        try {
            // 이미 대기중인 작업들을 모두 완료할 때 까지 10초 대기한다.
            if (!es.awaitTermination(10, SECONDS)) {
                // 정상 종료가 너무 오래 걸리면 ...
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow(); // 처리 중인 작업을 취소하고, 큐에 대기 중인 작업을 모두 제거한다.

                // 작업이 취소 될 때 까지 대기한다.
                if (!es.awaitTermination(10, SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination()으로 대기중인 현재 스레드가 인터럽트 될 수 있다.
            es.shutdownNow();
        }
    }
}
