package thread.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV3 {

    public static void main(String[] args) throws InterruptedException {
        log("Start");
        int start = 1, mid = 50, end = 100;
        SumTask task1 = new SumTask(start, mid);
        SumTask task2 = new SumTask(mid + 1, end);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        log("join() - main 스레드가 thread1, thread2 가 끝날 때까지 기다림");
        // 이때 main 스레드는 Waiting 상태가 된다.
        thread1.join();
        // thread1 끝나면 main 스레드는 Runnable 상태가 된다.

        // 이때 main 스레드는 Waiting 상태가 된다.
        thread2.join();
        // thread2 끝나면 main 스레드는 Runnable 상태가 된다.
        // 이루 아래 코드 실행됨

        // 현재 join의 단점 : 다른 스레드가 완료될 때 까지 무기한 기다리고 있음

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);
        log("End");
    }

    static class SumTask implements Runnable {
        int result = 0;
        private int startValue;
        private int endValue;

        public SumTask(int startValue, int end) {
            this.startValue = startValue;
            this.endValue = end;
        }

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            result = sum;
            log("작업 완료");
        }
    }

}