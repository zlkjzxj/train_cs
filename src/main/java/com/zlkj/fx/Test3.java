package com.zlkj.fx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test3 {

    //    private static Map<Integer, String> newDisks = new HashMap<>();//存放循环新的状态
    private static Map<Integer, String> newDisks = new HashMap<>();//存放循环新的状态
    private static Map<Integer, String> oldDisks = new HashMap<>();//存放循环上一次的状态

    private static Map<Integer, String> allDisks = new HashMap<>();


    public static void main(String[] args) {
        //1.新增的状态
//        newDisks.put(0, "126");
//        newDisks.put(1, "127");
//        oldDisks.put(1, "127");
        //2.新增的状态
//        oldDisks.put(0, "126");
//        oldDisks.put(1, "127");
//        newDisks.put(1, "127");
        //3.新增的状态
//        oldDisks.put(0, "126");
//        oldDisks.put(1, "127");
//        newDisks.put(0, "128");
//        newDisks.put(1, "129");
//        new Test3().calcDisksChange();
        allDisks.put(0, "1");
        allDisks.put(1, "0");
        allDisks.put(2, "0");
        int j = (int) getMinKey();
        System.out.println(j);
    }

    public Map<String, Map<Integer, String>> calcDisksChange() {
        Map<Integer, String> addDisks = new HashMap<>();//新旧比较存放增加的状态
        Map<Integer, String> rmDisks = new HashMap<>();//新旧比较存放移除的状态
        Map<String, Map<Integer, String>> adrmDisks = new HashMap<>();

        //循环旧的，判断新的是不否包含旧的，那就是移除的
        for (Map.Entry<Integer, String> entry : oldDisks.entrySet()) {
            if (newDisks.size() > 0 && !newDisks.containsValue(entry.getValue())) {
                rmDisks.put(entry.getKey(), entry.getValue());
            }
        }
        //循环新的，判断旧的是不否包含新的，那就是新增的
        for (Map.Entry<Integer, String> entry : newDisks.entrySet()) {
            if (oldDisks.size() > 0 && !oldDisks.containsValue(entry.getValue())) {
                addDisks.put(entry.getKey(), entry.getValue());
            }
        }

        adrmDisks.put("add", addDisks);
        adrmDisks.put("rm", rmDisks);
        return adrmDisks;
    }

    public static Object getMinKey() {
        if (allDisks.size() > 0) {
            Set<Integer> set = allDisks.keySet();
            Object[] obj = set.toArray();
            Arrays.sort(obj);
            for (int i = 0; i < obj.length; i++) {
                if (allDisks.get(i).equals("0")) {
                    return obj[i];
                }
            }
        }
        return -1;
    }
    /*public Map<String, Map<Integer, String>> calcDisksChange1() {
        Map<Integer, String> addDisks = new HashMap<>();//新旧比较存放增加的状态
        Map<Integer, String> rmDisks = new HashMap<>();//新旧比较存放移除的状态
        Map<String, Map<Integer, String>> adrmDisks = new HashMap<>();

        //循环旧的，判断新的是不否包含旧的，那就是移除的
        for (Map.Entry<Integer, String> entry : oldDisks.entrySet()) {
            if (newDisks.size() > 0 && !newDisks.containsValue(entry.getValue())) {
                rmDisks.put(entry.getKey(), entry.getValue());
            }
        }
        //循环新的，判断旧的是不否包含新的，那就是新增的
        for (Map.Entry<Integer, String> entry : newDisks.entrySet()) {
            if (oldDisks.size() > 0 && !oldDisks.containsValue(entry.getValue())) {
                addDisks.put(entry.getKey(), entry.getValue());
            }
        }

        adrmDisks.put("add", addDisks);
        adrmDisks.put("rm", rmDisks);
        return adrmDisks;
    }*/
}
