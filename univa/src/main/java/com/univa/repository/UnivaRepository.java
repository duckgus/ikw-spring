package com.univa.repository;

import com.univa.domain.UnivaUser;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UnivaRepository {
    private final EntityManager em;

    public UnivaRepository(EntityManager em) {
        this.em = em;
    }

    public UnivaUser save(UnivaUser user) {
        em.persist(user);
        return user;
    }

    public Optional<UnivaUser> findUserByUsernameAndPassword(UnivaUser user){
        return em.createQuery("select u from user u where username = :username AND password = :password", UnivaUser.class)
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword()).getResultList().stream().findAny();
    }

}
