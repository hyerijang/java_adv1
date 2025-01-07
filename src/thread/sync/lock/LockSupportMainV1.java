package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV1 {
    public static void main(String[] args)  throws InterruptedException {

        ParkTest parkTest = new ParkTest();
        Thread thread1 = new Thread(parkTest, "Thread-1");

        thread1.start();
        sleep(1000);
        log("Thread-1 state : " + thread1.getState());
        log("main -> Thread-1  unpark");

        // NOTE : WAITING인 스레드 1을 깨우는 방법
        // (1) unpark() 호출
        // (2)  interrupt() 호출
//        unpark(thread1);
        thread1.interrupt();
        log("main 종료");
    }

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park(); // NOTE : 현재 스레드를 WAITING 상태로 만듬
            log("park 종료, state :" + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }

    }
}
