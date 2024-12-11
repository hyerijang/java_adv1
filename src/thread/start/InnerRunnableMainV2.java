package thread.start;

import static util.MyLogger.log;

public class InnerRunnableMainV2 {
    public static void main(String[] args) {
        log("main() start" );

        // 익명 클래스 : 특정 메서드 안에서만 간단히 사용하고 싶을 때
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                log("run()");
            }
        };

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        log("main() end" );
    }

    // 이 클래스 안에서만 사용할 것이므로 static inner class (정적 중첩 클래스) 로 선언
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            log("Hello, Runnable!");
        }
    }
}
