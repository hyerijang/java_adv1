package thread.control;

import static util.MyLogger.log;
import thread.start.HelloRunnable;

public class ThreadInfoMain {
    public static void main(String[] args) {

        // main
        Thread mainThread = Thread.currentThread();
        log("mainThread.currentThread() = " + mainThread);
        log("mainThread.currentThread().getId() = " + mainThread.getId());
        log("mainThread.currentThread().getName() = " + mainThread.getName());
        log("mainThread.currentThread().getPriority() = " + mainThread.getPriority());
        log("mainThread.currentThread().getThreadGroup() = " + mainThread.getThreadGroup());
        log("mainThread.currentThread().getState() = " + mainThread.getState());

        //Threa
        Thread myThread = new Thread(new HelloRunnable(), "MyThread");
        log("myThread.currentThread() = " + myThread);
        log("myThread.currentThread().getId() = " + myThread.getId());
        log("myThread.currentThread().getName() = " + myThread.getName());
        log("myThread.currentThread().getPriority() = " + myThread.getPriority());
        log("myThread.currentThread().getThreadGroup() = " + myThread.getThreadGroup());
        log("myThread.currentThread().getState() = " + myThread.getState());
    }
}
