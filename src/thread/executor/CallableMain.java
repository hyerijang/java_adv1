package thread.executor;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.Random;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 기본 코드
        // ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        
        // 편의 코드
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new MyCallable());
        Integer result = future.get();
        log("result value = " + result);
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
