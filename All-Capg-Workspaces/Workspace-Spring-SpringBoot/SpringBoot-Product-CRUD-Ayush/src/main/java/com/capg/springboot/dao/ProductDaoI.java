
package com.capg.springboot.dao;

import java.util.List;

import com.capg.springboot.entities.Product;

public interface ProductDaoI {

    Product createProduct(Product product);

    Product findProductById(long productId);

    Product updateProduct(Product product);

    List<Product> findAllProducts();

    void deleteProduct(long productId);
}
