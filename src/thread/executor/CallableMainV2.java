package thread.executor;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableMainV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 기본 코드
        // ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        
        // 편의 코드
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new MyCallable());
        log("future 즉시 반환, future = " + future); // NOTE: 17:49:06.956 [     main] future 즉시 반환, future = java.util.concurrent.FutureTask@5b6f7412[Not completed, task = thread.executor.CallableMainV2$MyCallable@52cc8049]

        log("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING");
        Integer result = future.get(); // import : 결과가 나올때까지 메인 스레드가 기다림
        log("future.get() [블로킹] 메서드 호출 완료 -> main 스레드 RUNNABLE");


        log("result value = " + result);
        log("future.get() 이후, future = " + future); // NOTE: 17:49:06.956 [     main] future 즉시 반환, future = java.util.concurrent.FutureTask@5b6f7412[Not completed, task = thread.executor.CallableMainV2$MyCallable@52cc8049]
        executorService.close();
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("created value = " + value);
            log("Callable 완료");
            return value;
        }
    }
}
