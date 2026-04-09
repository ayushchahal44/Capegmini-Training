
package com.capg.springboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capg.springboot.entities.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDaoI {

    @PersistenceContext
    private EntityManager entityManager;

    public Product createProduct(Product product) {
        return entityManager.merge(product);
    }

    public Product findProductById(long productId) {
        return entityManager.find(Product.class, productId);
    }

    public Product updateProduct(Product product) {
        Product p = findProductById(product.getProductId());
        p.setProductName(product.getProductName());
        p.setProductPrice(product.getProductPrice());
        return entityManager.merge(p);
    }

    public List<Product> findAllProducts() {
        Query q = entityManager.createQuery("select p from Product p");
        return q.getResultList();
    }

    public void deleteProduct(long productId) {
        Product p = entityManager.find(Product.class, productId);
        entityManager.remove(p);
    }
}
