package com.thoughtworks.zhouxuan.repository;

import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.json.ProductInputJson;

import java.util.List;

public interface ProductRepository {
    public List<Product> getAllProducts();

    int saveProduct(ProductInputJson productInputJson);

    Product getProductById(int id);
}
