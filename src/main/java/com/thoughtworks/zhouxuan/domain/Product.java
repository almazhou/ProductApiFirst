package com.thoughtworks.zhouxuan.domain;

public class Product {
    private String name;
    private int id;

    public Product(String name) {
        this.name = name;
    }

    protected Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
