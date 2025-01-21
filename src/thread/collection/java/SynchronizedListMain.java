package thread.collection.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListMain {

    public static void main(String[] args) {
//        List<String> list  = new ArrayList<>();
        List<String> list  = Collections.synchronizedList(new ArrayList<>()); // NOTE : 프록시 패턴
        list.add("data1");
        list.add("data2");
        list.add("data3");
        System.out.println(list.getClass());
        System.out.println("list = " + list);
    }
}
