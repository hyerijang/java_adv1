package thread.start;

import static util.MyLogger.log;

// TODO : 익명 클래스로 구현
public class StartTest3Main {
    public static void main(String[] args) {
        //익명클래스
        Runnable runnable = new Runnable() {
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
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
