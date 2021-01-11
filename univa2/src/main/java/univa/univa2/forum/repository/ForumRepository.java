package univa.univa2.forum.repository;

import univa.univa2.forum.domain.ForumPost;
import univa.univa2.forum.domain.ForumUser;

import java.util.*;

import javax.persistence.EntityManager;

public class ForumRepository {
    private final EntityManager em;

    public ForumRepository(EntityManager em) {
        this.em = em;
    }

    public ForumUser save(ForumUser user) {
        return user;
    }

    public ForumPost save(ForumPost forum) {
        em.persist(forum);
        return forum;
    }
    public Optional<ForumUser> findUserByIdx(int idx) {
        ForumUser user = em.find(ForumUser.class, idx);
        return Optional.ofNullable(user);
    }

    public Optional<ForumUser> findUserByUsername(String username) {
        List<ForumUser> result = em.createQuery("select u from user u where u.username = :username", ForumUser.class)
                .setParameter("username", username)
                .getResultList();
        return result.stream().findAny();
    }
}
