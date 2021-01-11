package com.univa.repository;

import javax.persistence.EntityManager;

public class UnivaCRepository {

    private final EntityManager em;

    public UnivaCRepository(EntityManager em) {
        this.em = em;
    }


}
