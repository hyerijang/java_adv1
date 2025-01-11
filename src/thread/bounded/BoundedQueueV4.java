package thread.bounded;

import static util.MyLogger.log;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// NOTE : V4는 Lock을 사용해서 구현
// synchronized 사용시 발생하는 비효율을 해결했다.
//  while 사용해서 대기 -> 비정상 작동
public class BoundedQueueV4 implements BoundedQueue {


    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition(); // IMPORT : 대기집합 생성
    // Object.wait()에서 사용한 스레드 대기 공간은 ㄱ모든 객체 인스턴스가 내부에 기본으로 가지고 있는 공간이다.
    // NOTE: Condition.await()는 Lock 인스턴스에 스레드 대기공간을 직접 만들어서 사용 해야 한다.
    // TODO  : Object.wait, notify -> Condition.await, signal 로 변경
    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV4(int max) {
        this.max = max;
    }

    @Override
    public void put(String data) {
        try {
            lock.lock();
            try {
                while (queue.size() == max) {
                    log("[put] 큐가 가득 참, 생산자 대기");
                    condition.await(); // IMPORT Object.wait - > Condition.await 로 변경
                    log("[put] 생산자 깨어남");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            queue.offer(data);
            log("[put] 생산자 데이터 저장, signal() 호출");
            condition.signal(); // IMPORT Object.notify -> Condition.signal 로 변경

        } finally {
            lock.unlock();
        }
    }

    @Override
    public String take() {
        try {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    log("[take] 큐가 비어 있음, 소비자 대기");
                    condition.await(); // IMPORT Object.wait - > Condition.await 로 변경
                    log("[take] 소비자 깨어남");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String data = queue.poll();
            log("[take] 소비자 데이터 획득, signal() 호출");
            condition.signal(); // IMPORT Object.notify -> Condition.signal 로 변경
            return data;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() { // NOTE : synchronized 를 사용하지 않음 (원칙적으로는 사용하는게 맞지만, 예제에서는 데이터 확인이 크게 중요하지 않음 + 예제에서는 꼬일 수 있기 때문에 생략)
        return queue.toString();
    }
}
