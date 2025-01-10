package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import static util.MyLogger.log;

// synchronized 로 구현
public class BoundedQueueV1 implements BoundedQueue{

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV1(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        if (queue.size() == max) {
            log("[put] 큐가 가득 참, 버림");
        }
        queue.add(data);
    }

    @Override
    public synchronized String take() {
        if(queue.isEmpty()){
            log("[take] 큐가 비어 있음, null 반환");
            return null;
        }
        return queue.poll();
    }

    @Override
    public String toString() { // NOTE : synchronized 를 사용하지 않음 (원칙적으로는 사용하는게 맞지만, 예제에서는 데이터 확인이 크게 중요하지 않음 + 예제에서는 꼬일 수 있기 때문에 생략)
        return queue.toString();
    }
}
