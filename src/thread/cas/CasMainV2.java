package thread.cas;

import static util.MyLogger.log;
import java.util.concurrent.atomic.AtomicInteger;

public class CasMainV2 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());

        int result1 = incrementAndGet(atomicInteger);
        System.out.println("result1 = " + result1 + ", value = " + atomicInteger.get());

        int result2 = incrementAndGet(atomicInteger);
        System.out.println("result2 = " + result2 + ", value = " + atomicInteger.get());

    }

    // NOTE: 락을 걸지 않고 CAS 연산을 사용해서 값 증가
    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;
        do {
            getValue = atomicInteger.get(); //thread 1: 0
            log("getValue = " + getValue);
            //thread 2: 0 -> 1
            result =
                atomicInteger.compareAndSet(getValue, getValue + 1); // 읽은 시점의 값과 expected 값이 같으면 새로운 값(getValue+1)으로 변경
            log("result = " + result);
        } while (!result);
        return getValue + 1;
    }


}
