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
public class BoundedQueueV5 implements BoundedQueue {


    private final Lock lock = new ReentrantLock();

    // TODO  : 생산자, 소비자 대기공간 분리
    // IMPORT : 생산자는 소비자를 깨우고, 소비자는 생산자를 깨우도록한다
    private final Condition producerCond = lock.newCondition(); // 생산자 대기 공간
    private final Condition consumerond = lock.newCondition(); // 소비자 대기 공간

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV5(int max) {
        this.max = max;
    }

    @Override
    public void put(String data) {
        try {
            lock.lock();
            try {
                while (queue.size() == max) {
                    log("[put] 큐가 가득 참, 생산자 대기");
                    producerCond.await(); // IMPORT Object.wait - > Condition.await 로 변경
                    log("[put] 생산자 깨어남");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            queue.offer(data);
            log("[put] 생산자 데이터 저장, consumerond.signal() 호출");
            consumerond.signal(); // IMPORT Object.notify -> Condition.signal 로 변경

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
                    consumerond.await(); // IMPORT Object.wait - > Condition.await 로 변경
                    log("[take] 소비자 깨어남");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String data = queue.poll();
            log("[take] 소비자 데이터 획득, producerCond.signal() 호출");
            producerCond.signal(); // IMPORT Object.notify -> Condition.signal 로 변경
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
