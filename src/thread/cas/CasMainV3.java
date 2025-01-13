package thread.cas;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CasMainV3 {

    private static final int THREAD_COUNT = 2;
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int result = incrementAndGet(atomicInteger);
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int result = atomicInteger.get();
        System.out.println("result = " + result + ", value = " + atomicInteger.get());
    }

    // NOTE: 락을 걸지 않고 CAS 연산을 사용해서 값 증가
    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;
        do {
            sleep(100); // 스레드 동시 실행을 위한 대기
            getValue = atomicInteger.get(); //thread 1: 0
            log("getValue = " + getValue);
            //thread 2: 0 -> 1
            result =
                atomicInteger.compareAndSet(getValue, getValue + 1); // 읽은 시점의 값과 expected 값이 같으면 새로운 값(getValue+1)으로 변경
            log("result = " + result);
        } while (!result);
        return getValue + 1;
    }


}
