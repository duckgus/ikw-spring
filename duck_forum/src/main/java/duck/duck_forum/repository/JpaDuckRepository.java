package duck.duck_forum.repository;

import duck.duck_forum.domain.Duck_User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaDuckRepository implements DuckRepository{
    private final EntityManager em;

    public JpaDuckRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Duck_User save(Duck_User dUser) {
        em.persist(dUser);
        return dUser;
    }

    @Override
    public Optional<Duck_User> findByUsername(String username) {
        List<Duck_User> result = em.createQuery("select m from Duck_User m where m.username = :username", Duck_User.class)
                .setParameter("username", username).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Duck_User> findUserByUsernameAndPassword(Duck_User dUser) {
        return em.createQuery("select u from duck_user u where username = :username AND password = :password", Duck_User.class)
                .setParameter("username", dUser.getUsername())
                .setParameter("password", dUser.getPassword()).getResultList().stream().findAny();
    }

}
