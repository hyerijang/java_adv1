package thread.control;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CheckedExceptionMain {

    public static void main(String[] args) throws Exception {
        Runnable checkedRunnable = new CheckedRunnable();
        Thread thread = new Thread(checkedRunnable);
        thread.start();
    }

    static class CheckedRunnable implements Runnable {


        // 체크 예외 란? 컴파일 오류 나는 예외들, 예외 처리를 강제한다. (예시 :  throws Exception )
        // 상위 타입 메서드 (부모의 메서드)가 체크 예외를 던지지 않는 경우, 재정의 된 자식 메서드도 체크 예외를 던질 수 없다.
//        @Override
//        public void run() throws Exception {
//            throw new Exception("Checked Exception");
//        }

        @Override
        public void run() {
            log("CheckedRunnable.run()");
            sleep(1000);
//            throw new Exception("Checked Exception");
        }

    }
}
