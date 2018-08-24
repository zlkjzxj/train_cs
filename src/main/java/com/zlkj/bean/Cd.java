package com.zlkj.bean;

import java.util.List;

public class Cd {
    private String name;
    private String value;
    private List<Cc> cz;

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

    public List<Cc> getCz() {
        return cz;
    }

    public void setCz(List<Cc> cz) {
        this.cz = cz;
    }
}
