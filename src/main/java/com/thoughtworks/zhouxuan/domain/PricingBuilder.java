package com.thoughtworks.zhouxuan.domain;

public class PricingBuilder {
    private int id;
    private double amount;
    private int productId;

    public PricingBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PricingBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public PricingBuilder setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public Pricing build() {
        return new Pricing(id,amount,productId);
    }
}
