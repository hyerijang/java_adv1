package thread.collection.java;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetMain {

    public static void main(String[] args) {
        Set<Integer> copySet = new CopyOnWriteArraySet<>(); // NOTE : HashSet의 대안
        copySet.add(1);
        copySet.add(2);
        copySet.add(3);
        System.out.println("copySet = " + copySet); // import : 멀티 스레딩 환경에서는 순서 보장 X

        ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet(); //  NOTE :  TreeSet의 대안
        concurrentSkipListSet.add(3);
        concurrentSkipListSet.add(2);
        concurrentSkipListSet.add(1);
        System.out.println("concurrentSkipListSet = " + concurrentSkipListSet); // import : 정렬된 순서 유지

    }
}
