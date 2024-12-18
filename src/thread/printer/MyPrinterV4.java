package thread.printer;

import static util.MyLogger.log;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyPrinterV4 {

    public static void main(String[] args) {

        Printer printer = new Printer();
        Thread thread = new Thread(printer, "myPrinter-1");
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요. 종료 (q):");
            String job = scanner.nextLine();
            if (job.equals("q")) {
                thread.interrupt();
                break;
            }
            printer.addJob(job);
        }

        log("프린터 종료 지시");
        thread.interrupt();
    }

    static class Printer implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            // NOTE : 이런 while 문은 cpu를 많이 소모함. (cpu가 좋으면 ... 초 당 수십억번 반복될 수 있음)
            //   - while (!Thread.interrupted()) { if (jobQueue.isEmpty())
            while (!Thread.interrupted()) {
                if (jobQueue.isEmpty()) { // 체크 로직이 cpu를 많이 소모하고 있음.
                    // yield 가 없을 때 평균 12% cpu 사용
                    // yield 가 있을 때 평균 5% cpu 사용
                    Thread.yield(); // note: 따라서 큐가 빈 경우에는, 다른 스레드에 cpu 양보하도록 yield 추가
                    continue;
                }
                try {
                    String job = jobQueue.poll();
                    log("출력 시작 :  " + job + " 대기 문서 : " + jobQueue);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log("프린터 인터럽트!");
                    break;
                }
            }
            log("프린터 종료");
        }

        public void addJob(String job) {
            jobQueue.offer(job);
        }
    }
}
