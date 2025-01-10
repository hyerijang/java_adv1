package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountV6 implements BankAccount {

    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountV6(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        //  === 임게 영역 시작 ===
        try {

            if(!lock.tryLock(500, TimeUnit.MILLISECONDS)){
                // NOTE: 0.5초 동안 lock 획득 시도 (도중에 인터럽트 발생 시 시도를 포기)
                // tryLock(시간) : 락을 획득 할 수 없으면 fasle 반환
                log("[진입 실패] 이미 처리중인 작업이 있습니다.");
                return false;
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            // 잔고가 출금액 보다 적으면 진행하면 안됨
            log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }
            //잔고가 출금액 보다 많으면 진행
            log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            sleep(1000); // 출금에 걸리는 시간으로 가정
            balance -= amount;
            log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
            // === 임게 영역 종료 ===
        } finally {
            lock.unlock(); // TODO: (중요) finally 블록을 사용해서 unlock을 꼭 해줘야 함. 안하면 다른 스레드가 lock을 얻지 못하고 무한 대기 상태에 빠짐
        }
        log("거래 종료: " + getClass().getSimpleName());
        return true;
    }

    @Override
    public int getBalance() {
        // TODO : 여기도 lock 설정!
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
