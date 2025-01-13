package thread.cas.spinlock;

import static util.MyLogger.log;

public class spinLockMain {

    public static void main(String[] args) {
        SpinLockBad spinLock = new SpinLockBad();

        Runnable task = () -> {
            spinLock.lock();
            try {
                log("비즈니스 로직 실행");
            } finally {
                spinLock.unlock();
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        thread1.start();
        thread2.start();
    }
}
