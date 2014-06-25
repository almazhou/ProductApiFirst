package com.thoughtworks.zhouxuan.domain;

public class Pricing {
    private final double amount;
    private  int id;
    private  int productId;

    public Pricing(double amount) {
        this.amount = amount;
    }

    public Pricing(int id, double amount, int productId) {
        this.id = id;
        this.amount = amount;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public double getAmount() {
        return amount;
    }
}
