package thread.bounded;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.ArrayDeque;
import java.util.Queue;

// synchronized 로 구현
// NOTE : V2 는 비정상적으로 작동하는 예제
//  while 사용해서 대기 -> 비정상 작동
public class BoundedQueueV3 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                wait(); // NOTE : 현재 스레드가 가진 "락을 반납"하고 "RUNNABLE -> WAITING"
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log("[put] 생산자 데이터 저장, notify() 호출" );
        queue.offer(data);
        notify(); // NOTE : 대기하고 있는 다른 스레드중 하나를 깨운다("WAIT -> BLOCKED").
        // NOTE : BLOCKED??? -> RUNNABLE로 전환되기 위해 락을 얻어야함
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐가 비어 있음, 소비자 대기");
            try {
                wait();
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출" );
        notify();
        return data;
    }

    @Override
    public String toString() { // NOTE : synchronized 를 사용하지 않음 (원칙적으로는 사용하는게 맞지만, 예제에서는 데이터 확인이 크게 중요하지 않음 + 예제에서는 꼬일 수 있기 때문에 생략)
        return queue.toString();
    }
}
