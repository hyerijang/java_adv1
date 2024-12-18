package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {
    public static void main(String[] args) {

        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "work");
        thread.start();
        log("runFlag = " + myTask.runFlag);


        sleep(1000);
        log("runFlag=false로 변경 시도");
        myTask.runFlag = false;
        log("runFlag = " + myTask.runFlag);
        log("main 종료");
    }

    static class MyTask implements Runnable {
//        volatile boolean runFlag = true;
        boolean runFlag = true; // NOTE :  volatile 키워드를 제거하면 어떻게 될까요? -> myTask.runFlag = false;로 변경해도 작업이 종료되지 않습니다.

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                // 뭔가 출력하면 안됨
                // runFlag가 false로 변하면 탈출
            }
            log("task 종료"); // NOTE: 여기가 출력 안됨. 프로그램 종료도 안됨.
        }
    }
}
