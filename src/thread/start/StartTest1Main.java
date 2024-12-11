package thread.start;

import static util.MyLogger.log;

// TODO :  CounterThread 를 만들기
public class StartTest1Main {
    public static void main(String[] args) {
        CounterThread counterThread = new CounterThread();
        counterThread.start();
    }

    static class CounterThread extends Thread {
        @Override
        public void run() {
            for (int value = 1; value <= 5; value++) {
                try {
                    log("value: " + value);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
