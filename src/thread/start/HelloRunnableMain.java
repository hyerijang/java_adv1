package thread.start;

public class HelloRunnableMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ".main() start");
        System.out.println(Thread.currentThread().getName() + ".start() 호출 전");
        Thread thread = new Thread(new HelloRunnable());
        thread.start(); // Thread-0.run()
        System.out.println(Thread.currentThread().getName() + ".start() 호출 후");
        System.out.println(Thread.currentThread().getName() + ".main() end");
    }
}
