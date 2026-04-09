package com.capg.food.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.*;
import com.capg.food.entity.*;

@Repository
@Transactional
public class FoodItemDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(FoodItem f) {
        em.persist(f);
    }

    public FoodItem findById(Long id) {
        return em.find(FoodItem.class, id);
    }

    public List<FoodItem> findByCategory(Long categoryId) {
        return em.createQuery(
                "from FoodItem where category.id=:id",
                FoodItem.class)
                .setParameter("id", categoryId)
                .getResultList();
    }

    public void delete(Long id) {
        FoodItem f = em.find(FoodItem.class, id);
        if (f != null) em.remove(f);
    }
}