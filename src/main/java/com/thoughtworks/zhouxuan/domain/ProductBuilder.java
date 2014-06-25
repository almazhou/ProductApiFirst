package com.thoughtworks.zhouxuan.domain;

public class ProductBuilder {
    private int id;
    private String name;

    public ProductBuilder setId(int id) {
        this.id = id;
        return this;
    }


    public ProductBuilder setName(String productName) {
        this.name = productName;
        return this;
    }

    public Product build() {
        return new Product(id,name);
    }
}
