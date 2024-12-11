package thread.start;

import static util.MyLogger.log;

// TODO : 여러 스레드 사용
public class StartTest4Main {
    public static void main(String[] args) {
        //익명클래스
        Runnable runnableA = new PrintRunner(1000, "A");
//        Thread threadA = new Thread(runnableA);
//        threadA.setName("Thread-A");
        Thread threadA = new Thread(runnableA, "Thread-A"); // 위 두줄을 한줄로
        threadA.start();
        Runnable runnableB = new PrintRunner(500, "B");
        Thread threadB = new Thread(runnableB, "Thread-B");
        threadB.start();
    }

    static class PrintRunner implements Runnable {

        private final String content;
        private int sleepMs = 1000;

        public PrintRunner(int sleepMs, String content) {
            this.sleepMs = sleepMs;
            this.content = content;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    log(content);
                    Thread.sleep(sleepMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
