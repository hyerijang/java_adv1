package thread.bounded;

import static util.MyLogger.log;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

// NOTE: 일정시간 대기
public class BoundedQueueV6_3 implements BoundedQueue {

    private BlockingQueue<String> queue;

    public BoundedQueueV6_3(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        try {
            boolean result = queue.offer(data, 1, TimeUnit.NANOSECONDS);
            log("저장 시도 결과 = " + result);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String take() {
        try {
            return queue.poll(3, TimeUnit.SECONDS); // 2초 대기
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() { // NOTE : synchronized 를 사용하지 않음 (원칙적으로는 사용하는게 맞지만, 예제에서는 데이터 확인이 크게 중요하지 않음 + 예제에서는 꼬일 수 있기 때문에 생략)
        return queue.toString();
    }
}
