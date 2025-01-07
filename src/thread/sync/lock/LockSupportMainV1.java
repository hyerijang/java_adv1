package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;
import static java.util.concurrent.locks.LockSupport.unpark;
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
        // 스레드 1 깨운다.
        unpark(thread1);
    }

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료, state :" + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }

    }
}
