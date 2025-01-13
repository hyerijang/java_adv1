package thread.cas.spinlock;

import static util.MyLogger.log;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLock {
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        log("락 획득 시도");
        while (!lock.compareAndSet(false, true)) {
            log("락 획득 실패 - 스핀 대기");
        }

        // IMPORT : 참고로 아래는 잘못된 코드이다. (원자적이지 않음 -> 안전하지 않음, 이렇게 구현하면 안됨)
//        while (true) {
//            if (!lock.get()) { // (1) 값이 FALSE인지 확인
//                lock.compareAndSet(false, true); // (2)조회 후 변경 시도
//                break;
//            }
//        }
        log("락 획득 완료");
    }

    public void unlock() {
        lock.set(false);
        log("락 반납 완료");
    }
}
