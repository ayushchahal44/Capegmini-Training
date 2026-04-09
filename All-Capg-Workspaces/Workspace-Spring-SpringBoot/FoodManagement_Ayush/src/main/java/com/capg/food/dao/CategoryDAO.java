package com.capg.food.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.*;
import com.capg.food.entity.*;

@Repository
@Transactional
public class CategoryDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(Category c) {
        em.persist(c);
    }

    public List<Category> findAll() {
        return em.createQuery("from Category", Category.class)
                .getResultList();
    }

    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    public void delete(Long id) {
        Category c = em.find(Category.class, id);
        if (c != null) em.remove(c);
    }
}