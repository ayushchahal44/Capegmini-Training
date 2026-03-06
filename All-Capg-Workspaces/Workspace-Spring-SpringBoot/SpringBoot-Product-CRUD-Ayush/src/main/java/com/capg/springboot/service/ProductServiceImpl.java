
package com.capg.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.springboot.dao.ProductDaoI;
import com.capg.springboot.entities.Product;

@Service
public class ProductServiceImpl implements ProductServiceI {

    @Autowired
    private ProductDaoI dao;

    public Product createProduct(Product product) {
        return dao.createProduct(product);
    }

    public Product findProductById(long productId) {
        return dao.findProductById(productId);
    }

    public Product updateProduct(Product product) {
        return dao.updateProduct(product);
    }

    public List<Product> findAllProducts() {
        return dao.findAllProducts();
    }

    public void deleteProduct(long productId) {
        dao.deleteProduct(productId);
    }
}
