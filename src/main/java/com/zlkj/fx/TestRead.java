package com.zlkj.fx;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TestRead {
    public static void main(String[] args) throws IOException {
        File file = new File("E:/fuck.txt");

        StringBuilder sb = new StringBuilder();
        String s = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((s = br.readLine()) != null) {
//            sb.append(s + "\n");
            sb.append(s);
        }

        br.close();
        String str = sb.toString();
        System.out.println(str);
        List<String> xx = Arrays.asList(str.split(","));
        System.out.println(xx);
    }
}
