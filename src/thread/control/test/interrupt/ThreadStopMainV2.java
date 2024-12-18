package thread.control.test.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV2 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        thread.interrupt();
        log("work 스레드 인터럽트 상태 1 " + thread.isInterrupted()); // 인터럽트 걸린 상태 (true)
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    log("작업 중"); // 이부분에서 interrupt()가 호출되면, InterruptedException 발생 XXXXX!!!!
                    Thread.sleep(3000); // InterruptedException 발생 시키는 메소드가 실행 중일 때, 인터럽트 걸리면, InterruptedException 발생
                }

            } catch (InterruptedException e) {
                // NOTE : 여기서 인터럽트가 풀린다.
                // 인터럽트 걸려서 깨어나면, isInterrupted()는 false로 초기화된다.
                // 그래서, isInterrupted()를 호출하면, false가 나온다.
                log("work 스레드 인터럽트 상태 2 " + Thread.currentThread().isInterrupted()); // false !
                log("interrupt message = " + e.getMessage());
                log("state = " + Thread.currentThread().getState());
            }
            log("자원 정리");
            log("작업 종료");

        }
    }
}
