package com.zlkj.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sun.org.apache.xalan.internal.xsltc.dom.DocumentCache;
import com.zlkj.bean.Cd;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFile {
    public static String readJsonFile() {
//        String path = getClass().getClassLoader().getResource("data/xx.txt").toString();
        String path = "E:\\ideaworkspace\\train_cs\\target\\classes\\data\\select.json";
        System.out.println(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Map<String, Object> map = new Gson().fromJson(laststr, new TypeToken<HashMap<String, Object>>() {
//        }.getType());
        return laststr;
    }

    public static void main(String[] args) {

//        String cc = new JsonFile().readJsonFile();
        String s = JsonFile.readJsonFile();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray Jarray = parser.parse(s).getAsJsonArray();
        String[] cc = new String[]{};
        while (Jarray.iterator().hasNext()) {

        }
        System.out.println("sadfsaf");
//        Map<String, Object> map = new JsonFile().readJsonFile();
    }
}
