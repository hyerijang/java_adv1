package thread.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV2 {

    public static void main(String[] args) {
        log("Start");
        int start = 1, mid = 50, end = 100;
        SumTask task1 = new SumTask(start, mid);
        SumTask task2 = new SumTask(mid + 1, end);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        sleep(3000); // 실제로는 정확한 타이밍 맞추기 어려움

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