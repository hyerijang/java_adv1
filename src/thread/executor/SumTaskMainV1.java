package thread.executor;

import static util.MyLogger.log;

public class SumTaskMainV1 {

    public static void main(String[] args) throws InterruptedException {
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");

        t1.start();
        t2.start();

        log("join() - main 스레드가 t1, t2 종료까지 대기");
        t1.join();
        t2.join();
        log("main 스레드 대기 완료");

        log("task1.result =" + task1.result);
        log("task2.result =" + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);
        log("End");
    }

    static class SumTask implements Runnable {
        int startValue;
        int endValue;
        int result = 0;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");

            //예외를 잡았어야 한다.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }

            result = sum;

            log("작업 완료 result = " + sum);
        }
    }
}

