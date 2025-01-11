package thread.bounded;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.ArrayList;
import java.util.List;

public class BoundedMain {

    public static void main(String[] args) {

        // 1. BoundedQueue 선택
        BoundedQueue queue = new BoundedQueueV4(2);

        // 2. 생산자, 소비자 실행 순서 선택, 반드시 하나만 선택!
        producerFirst(queue); // 생산자 먼저 실행
//        consumerFirst(queue); // 소비자 먼저 실행
    }

    private static void producerFirst(BoundedQueue queue) {

        log("=== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ===");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllstate(queue, threads);
        startConsumer(queue, threads);
        printAllstate(queue, threads);
        log("=== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ===");

    }

    private static void consumerFirst(BoundedQueue queue) {
        log("=== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ===");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllstate(queue, threads);
        startProducer(queue, threads);
        printAllstate(queue, threads);
        log("=== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ===");
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("[소비자 시작]");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            sleep(100); // 예제를 보기 쉽게 만들기 위해 추가함(1,2,3 순차적으로 consume 하기위함)
        }
    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();

        log("[생산자 시작]");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            sleep(100); // 예제를 보기 쉽게 만들기 위해 추가함(1,2,3 순차적으로 produce 하기위함)
        }
    }

    private static void printAllstate(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("[현재 상태 출력, 큐 데이터 :" + queue);
        for (Thread thread : threads) {
            log(thread.getName() + " : " + thread.getState());
        }
    }

}
