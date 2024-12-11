package thread.start;

public class DeamonThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ".main() start");
        System.out.println(Thread.currentThread().getName() + ".start() 호출 전");
        DemonThread demonThread = new DemonThread();
        demonThread.setDaemon(true); // 데몬 스레드 여부
        demonThread.start(); // Thread-0.run()
        System.out.println(Thread.currentThread().getName() + ".start() 호출 후");
        System.out.println(Thread.currentThread().getName() + ".main() end");
    }

    static class DemonThread extends Thread { // inner 클래스는 메모리 누스 위험 있으므로 inner static으로 선언
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ".run() start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ".run() end");
        }
    }

}

