package thread.sync.test;

import static util.MyLogger.log;

public class SyncTest1BadMain {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10_000; i++) {
                    counter.increment();
                    log("count = " + counter.getCount());
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("결과: " + counter.getCount());

    }

    static class Counter {
        private int count = 0;

        // synchronized 키워드
        public synchronized void increment() {
                count = count + 1;
        }

        // NOTE: getCount에도 synchronized 키워드
        public synchronized int getCount() {
            return count;
        }
    }
}
