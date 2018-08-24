package com.zlkj.fx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test1 {
    private static Map<Integer, String> useDisks = new HashMap<>();
    private static Map<Integer, String> disks1 = new HashMap<>();

    //    public static void main(String[] args) {
//        useDisks.put(0, "0");
//        useDisks.put(1, "1");
//        disks1.put(0, "0");
//        Iterator<Integer> iter1 = useDisks.keySet().iterator();
//
//        while (iter1.hasNext()) {
//            Integer m1Key = (Integer) iter1.next();
//            if (disks1.size() > 0 && !disks1.containsKey(m1Key)) {//若两个map中相同key对应的value不相等
//                System.out.println(iter1.next());
//            }
//        }
//    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            double a = Math.random();
            System.out.println(a);
        }
    }
}
