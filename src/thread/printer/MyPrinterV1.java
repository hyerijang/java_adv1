package thread.printer;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyPrinterV1 {

    public static void main(String[] args) {

        Printer printer = new Printer();
        Thread thread = new Thread(printer, "myPrinter-1");
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요. 종료 (q):");
            String job = scanner.nextLine();
            if (job.equals("q")) {
                printer.work = false;
                break;
            }

            printer.addJob(job);
        }
        log("프린터 종료 지시");
    }

    static class Printer implements Runnable {
        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (work) {
                if (jobQueue.isEmpty()) {
                    continue;
                }
                String job = jobQueue.poll();
                log("출력 시작 :  " + job + " 대기 문서 : " + jobQueue);
                sleep(3000);
            }
            log("프린터 종료");
        }

        public void addJob(String job) {
            jobQueue.offer(job);
        }
    }
}
