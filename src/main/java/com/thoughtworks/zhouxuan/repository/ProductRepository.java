package com.thoughtworks.zhouxuan.repository;

import com.thoughtworks.zhouxuan.domain.Pricing;
import com.thoughtworks.zhouxuan.domain.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> getAllProducts();

    void saveProduct(Product product);

    Product getProductById(int id);

    List<Pricing> getAllPricingsOfProduct(int productid);

    void savePricing(Product product, Pricing pricingInputJson);
}
