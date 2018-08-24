package com.zlkj.bean;

import java.util.List;

public class Cz {
    private String name;
    private String value;
    private List<Cc> cc;

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

    public List<Cc> getCc() {
        return cc;
    }

    public void setCc(List<Cc> cc) {
        this.cc = cc;
    }
}
