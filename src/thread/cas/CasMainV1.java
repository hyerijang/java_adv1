package thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CasMainV1 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        // NOTE : compareAndSet(기대하는 값, 변경할 값)
        // 1. if 기대하는 값이 0이면, 2.값을 1로 변경해
        // cpu 에서 위 연산을 원자적으로 만들어 준다. (CAS)
        boolean result1 = atomicInteger.compareAndSet(0, 1);
        System.out.println("result1 = " + result1 + ", value = " + atomicInteger.get());

        boolean result2 = atomicInteger.compareAndSet(0, 1);
        System.out.println("result2 = " + result2 + ", value = " + atomicInteger.get());

    }
}
