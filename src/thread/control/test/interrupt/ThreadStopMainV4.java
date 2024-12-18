package thread.control.test.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV4 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(100);
        thread.interrupt();
        log("work 스레드 인터럽트 상태 1 " + thread.isInterrupted()); // 인터럽트 걸린 상태 (true)
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {

            while (!Thread.interrupted()) { // 인터럽트 상태 변경 O
                log("작업 중");
            }

            log("work 스레드 인터럽트 상태 2 " +
                Thread.currentThread().isInterrupted()); // FIXME : 인터럽트 상태가 true로 유지됨. false로 바꿔야합니다.
            log("state = " + Thread.currentThread().getState());

            try {
                log("자원 정리");
                Thread.sleep(1000); // 인터럽트 상태가 true이므로  sleep 실행하자 마자 -> InterruptedException 발생
                log("작업 종료");
            } catch (InterruptedException e) {
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생, " + e.getMessage());
                log("work 스레드 인터럽트 상태 3 " +
                    Thread.currentThread().isInterrupted()); // false (예외가 터지면서 인터럽트 상태가 정상적으로 변겨되었음)
            }
        }
    }
}
