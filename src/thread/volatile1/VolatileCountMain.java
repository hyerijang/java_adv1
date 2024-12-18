package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileCountMain {
    public static void main(String[] args) {

        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "work");
        thread.start();
        log("runFlag = " + myTask.runFlag);


        sleep(1000);

        myTask.runFlag = false;
        log("flag = " +   myTask.runFlag + ", count = " + myTask.count + " in main()");

        log("main 종료");
    }

    static class MyTask implements Runnable {
        volatile boolean runFlag = true;
        volatile long count = 0;

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                count++;
                if (count % 100_000_000 == 0) {
                    log("flag = " + runFlag + ", count = " + count + " in while()");
                }
            }
            log("flag = " + runFlag + ", count = " + count + " 종료");
        }
    }
}
