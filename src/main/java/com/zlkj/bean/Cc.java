package com.zlkj.bean;

import java.util.List;

public class Cc {
    private String name;
    private String value;
    private List<Cx> cx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Cx> getCx() {
        return cx;
    }

    public void setCx(List<Cx> cx) {
        this.cx = cx;
    }
}
