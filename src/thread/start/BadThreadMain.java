package thread.start;

public class BadThreadMain extends Thread {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ".main() start");
        System.out.println(Thread.currentThread().getName() + ".start() 호출 전");
        HelloThread helloThread = new HelloThread();
        helloThread.run(); // main.run()
        System.out.println(Thread.currentThread().getName() + ".start() 호출 후");
        System.out.println(Thread.currentThread().getName() + ".main() end");
    }
}
