package com.capg.assessment.util;

import javax.persistence.*;

public class JPAUtil {

    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("crmPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}