package thread.start;

public class HelloThreadMain extends Thread {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ".main() start");
        System.out.println(Thread.currentThread().getName() + ".start() 호출 전");
        HelloThread helloThread = new HelloThread();
        // run() 이 아니라 start()를 호출해야함 그래야 별도의 스레드에서 run()이 실행됨
        helloThread.start(); // Thread-0.run()
        System.out.println(Thread.currentThread().getName() + ".start() 호출 후");
        System.out.println(Thread.currentThread().getName() + ".main() end");
    }
}
