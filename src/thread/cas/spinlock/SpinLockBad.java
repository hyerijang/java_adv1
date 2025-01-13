package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockBad {
    private volatile boolean lock = false;

    // note : 왜 문제가 될까요?
    // 원자적이지 않기 때문입니다.
    // lock 변수를 읽고 값을 변경하는 동안 다른 스레드가 lock 변수를 변경할 수 있습니다.
    public void lock() {
        log("락 획득 시도");
        while (true) {
            if (!lock) {
                sleep(100); // 문제 상황 확인용, 스레드 대기
                lock = true;
                break;
            } else {
                log("락 획득 실패 - 스핀 대기");
            }
        }
        log("락 획득 완료");
    }

    public void unlock() {
        lock = false;
        log("락 반납 완료");
    }
}
