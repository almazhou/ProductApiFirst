package com.thoughtworks.zhouxuan.json;

import com.thoughtworks.zhouxuan.domain.Pricing;

public class PricingInputJson {
    private double amount;

    public void setAmount(String amount){
        this.amount = Double.valueOf(amount).doubleValue();
    }

    public double getAmount() {
        return amount;
    }

    public Pricing getPricing(){
        return new Pricing(amount);
    }

}
