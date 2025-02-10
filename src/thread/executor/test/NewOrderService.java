package thread.executor.test;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class NewOrderService {

    public void order(String orderNo) {

        ExecutorService es = newFixedThreadPool(3); // NOTE : 작업이 3개니까 일단 3개로 설정함
        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);

        // 작업 요청
        List<Callable<Boolean>> works = List.of(inventoryWork, shippingWork, accountingWork);

        // 결과 확인
        try {
            List<Future<Boolean>> futures = es.invokeAll(works);
            for (Future<Boolean> future : futures) {
                if (!future.get()) {
                    log("일부 작업이 실패했습니다.");
                    return;
                }
            }
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        } catch (InterruptedException e) {
            log("주문 처리 중 문제가 발생했습니다."); // Note : 아마도.. 작업중인 스레드에 인터럽트 걸리면 여기로 올듯? 일단 ExecutionException랑 분리해두긴 했는데 그냥 묶어도 되나?
        }
        catch (ExecutionException e) {
            log("일부 작업이 실패했습니다.");
        } finally {
            es.close();
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
