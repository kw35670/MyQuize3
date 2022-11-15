package com.example.myquize3;

import java.util.ArrayList;
import java.util.List;

public class Sample {
    private String name;
    private List<String> phoneList;

    public Sample() { }
    public Sample(String name, List<String> phoneList) {
        this.name = name;
        this.phoneList = phoneList;
    }
    public String getName() { return name; }
    public List<String> getPhoneList() { return phoneList; }
    public String toString() {
        return "name:"+name+" phone:"+phoneList;
    }
}
