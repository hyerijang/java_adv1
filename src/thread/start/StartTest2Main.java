package thread.start;

import static util.MyLogger.log;

// TODO : CounterRunnable 만들기
public class StartTest2Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new CounterRunnable());
        thread.start();
    }

    static class CounterRunnable implements Runnable {
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
