package com.thoughtworks.zhouxuan.json;

import com.thoughtworks.zhouxuan.domain.Pricing;
import com.thoughtworks.zhouxuan.domain.Product;

import javax.ws.rs.core.UriInfo;

public class PricingJson {
    private Pricing pricing;
    private Product product;
    private UriInfo uriInfo;

    public PricingJson(Pricing pricing, Product product, UriInfo uriInfo) {

        this.pricing = pricing;
        this.product = product;
        this.uriInfo = uriInfo;
    }

    public int getId(){
        return pricing.getId();
    }

    public int getProductId(){
        return pricing.getProductId();
    }

    public double getAmount(){
        return pricing.getAmount();
    }

    public String getUri(){
        return uriInfo.getBaseUri()+"products/"+String.valueOf(pricing.getProductId())+"/pricings/"+String.valueOf(pricing.getId());
    }
}
