package univa.forum.repository;

import univa.forum.domain.ForumUser;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ForumRepository {
    private final EntityManager em;

    public ForumRepository(EntityManager em) {
        this.em = em;
    }

    public ForumUser save(ForumUser user) {
        em.persist(user);
        return user;
    }

    public Optional<ForumUser> findUserByUsername(String username) {
        List<ForumUser> result = em.createQuery("select u from user u where u.username = :username", ForumUser.class)
                .setParameter("username", username)
                .getResultList();
        return result.stream().findAny();
    }

    public Optional<ForumUser> findUserByUsernameAndPassword(ForumUser user) {
        return em.createQuery("select u from user u where username = :username AND password = :password", ForumUser.class)
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword()).getResultList().stream().findAny();
    }
}
