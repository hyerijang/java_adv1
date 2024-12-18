package thread.printer;

import static util.MyLogger.log;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyPrinterV2 {

    public static void main(String[] args) {

        Printer printer = new Printer();
        Thread thread = new Thread(printer, "myPrinter-1");
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요. 종료 (q):");
            String job = scanner.nextLine();
            if (job.equals("q")) {
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
            while (!Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
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
