package com.thoughtworks.zhouxuan.json;

import com.thoughtworks.zhouxuan.domain.Product;

import javax.ws.rs.core.UriInfo;

public class ProductJson {
    private  Product product;
    private  UriInfo uriInfo;

    public ProductJson(Product product, UriInfo uriInfo) {
        this.product = product;
        this.uriInfo = uriInfo;
    }

    public String getName(){
        return product.getName();
    }
}
