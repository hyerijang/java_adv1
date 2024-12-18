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
        // NOTE : volatile 키워드를 추가하면, 캐시 메모리를 사용하지 않고 메민 메모리에서 값을 읽어옵니다.
        //   - 캐시 메모리 쓸 때 보다 약간 성능 저하가 있긴 함. 따라서 꼭 필요한 곳에만 쓰는게 좋다.
        volatile boolean runFlag = true;
//        boolean runFlag = true; // NOTE :  volatile 키워드를 제거하면 어떻게 될까요? -> myTask.runFlag = false;로 변경해도 작업이 종료되지 않습니다.

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                // XXX : 뭔가 출력하면 volatile없을 때의 문제점 재현 할 수 없음 -> 컨텍스트 스위칭 일어나면서  캐시 메모리 업데이트;;
                // runFlag가 false로 변하면 탈출
            }
            log("task 종료"); // NOTE: 여기가 출력 안됨. 프로그램 종료도 안됨.
        }
    }
}
