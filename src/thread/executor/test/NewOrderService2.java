package thread.executor.test;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class NewOrderService2 {

    // Note : 김영한 풀이
    public void order(String orderNo) throws ExecutionException, InterruptedException {

        ExecutorService es = newFixedThreadPool(3); // NOTE : 작업이 3개니까 일단 3개로 설정함
        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);

        // 작업 요청
        // Note : 풀이에서는 submit을 사용했다. (기존 코드 최대한 덜 수정하는 방향으로 한듯?? ) 내가 했던 것 처럼 invokeAll 써도 상관 없음.
        Future<Boolean> inventoryFuture = es.submit(inventoryWork);
        Future<Boolean> shippingFuture = es.submit(shippingWork);
        Future<Boolean> accountingFuture = es.submit(accountingWork);

        // 작업 완료를 기다림
        //  Note : 예제를 단순화 하기 위해 ExecutionException, InterruptedException  예외를 따로 처리하지 않고 그냥 던짐
        Boolean inventoryResult = inventoryFuture.get();
        Boolean shippingResult = shippingFuture.get();
        Boolean accountingResult = accountingFuture.get();

        // 결과 확인
        if (inventoryResult && shippingResult && accountingResult) {
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        } else {
            log("일부 작업이 실패했습니다.");
        }
    }

    static class InventoryWork implements Callable<Boolean> {
        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {
        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {
        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }

    }

}
