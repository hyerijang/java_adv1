package thread.start;

public class HelloRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ".run() start");
        System.out.println(Thread.currentThread().getName() + ".run() end");
    }
}
