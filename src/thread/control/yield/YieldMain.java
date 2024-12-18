package thread.control.yield;

public class YieldMain {
    static final int THREAD_COUNT = 1000;

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyTask(), "Thread-" + i);
            thread.start();
        }
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                // 1. empty : 한 스레드가 연속해서 작업 (운영체제마다 다르지만... 0.2초?)

                // 2. Thread.sleep(1) : 1개 출력후 다른 스레드 (매번 출력하는 스레드가 바뀜)
                // FIXME : sleep의 문제점
                //  1. 양보할 스레드가 없어도 무조건 쉼 (스레드가 널널하면 그냥 작업 계속 해도 되는데...)
                //  2. RUNNABLE <-> TIMED_WAITING
//                sleep(1);

                // 3. Thread.yield(); : empty랑 sleep의 중간 정도?
                // runnable <-> ready // 자바에서는 두 상태 구분안하고 RUNABLE로 퉁치긴 함
                // TODO
                //   1. 중요 ! *RUNNABLE 상태를 유지*하면서 CPU 를 양보
                // 운영체제의 스케쥴러에게 단지 힌트를 제공할 뿐, 강제적인 실행 순서를 지정하는 건 아님
                // 그리고 반드시 다른 스레드가 실행되는 것도 아님

                // RUNNBALE 상태를 유지하기 때문에, 즉 양보할 사람이 없으면 본인 스레드가 계속 실행된다.
                Thread.yield();
            }
        }
    }
}
