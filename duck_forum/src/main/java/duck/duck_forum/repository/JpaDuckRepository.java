package duck.duck_forum.repository;


import duck.duck_forum.domain.Duck_User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaDuckRepository implements DuckRepository{

    private final EntityManager entityManager;

    public JpaDuckRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Duck_User save(Duck_User user) {
        entityManager.persist(user);
        return user;
    }



    @Override
    public Optional<Duck_User> findByUsername(String user) {
        System.out.println("[findByUsername] user : " + user);
        List<Duck_User> result = entityManager.createQuery("select m from Duck_User m where m.username = :username", Duck_User.class)
                .setParameter("username", user).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Duck_User> findByUsernameANDPassword(Duck_User user) {
        System.out.println("[findByUsernameANDPassword] user : " + user);
        List<Duck_User> result = entityManager.createQuery("select u from Duck_User u where username = :username AND password = :password", Duck_User.class)
                .setParameter("username", user)
                .setParameter("password", user).getResultList();
        return result.stream().findAny();
    }

}
