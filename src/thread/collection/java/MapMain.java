package thread.collection.java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapMain {
    public static void main(String[] args) {
        Map<Integer, String> map1 = new ConcurrentHashMap<>(); // Note : Map의 대안 (순서 보장 X)
        map1.put(1, "data1");
        map1.put(3, "data3");
        map1.put(2, "data2");
        System.out.println("map1 = " + map1);

        Map<Integer, String> map2 = new ConcurrentSkipListMap<>(); // Note : Tree Map의 대안 (정렬 순서 보장)
        map2.put(1, "data1");
        map2.put(3, "data3");
        map2.put(2, "data2");
        System.out.println("map2 = " + map2);

    }
}
