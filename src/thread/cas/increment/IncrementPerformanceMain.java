package thread.cas.increment;

import java.util.ArrayList;
import java.util.List;
import static util.ThreadUtils.sleep;

public class IncrementPerformanceMain {
    public static final int THREAD_COUNT = 1000;
    public static final long COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger incrementInteger) throws InterruptedException {
        long startMs = System.currentTimeMillis();

        for (int i = 0; i < COUNT; i++) {
            incrementInteger.increment();
        }
        long endMs = System.currentTimeMillis();
        System.out.println(incrementInteger.getClass().getSimpleName() + " : " + (endMs - startMs) + "ms");

    }
}
