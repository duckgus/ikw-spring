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
        List<Duck_User> result = entityManager.createQuery("select m from Duck_User m where username = :username", Duck_User.class)
                .setParameter("username", user).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Duck_User> findByUsernameANDPassword(Duck_User user) {
        System.out.println("[findByUsernameANDPassword] user : " + user);
        List<Duck_User> result = entityManager.createQuery("select u from Duck_User u where username = :username AND password = :password", Duck_User.class)
                .setParameter("username", user.getUsername())
                .setParameter("password", user.getPassword()).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Duck_User> findByUseremail(String user) {
        System.out.println("[findByUseremail] user : " + user);
        List<Duck_User> result = entityManager.createQuery("select m from Duck_User m where email = :email", Duck_User.class)
                .setParameter("email", user).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Duck_User> findByUseremailANDPassword(Duck_User user) {
        System.out.println("[findByUseremailANDPassword] user : " + user);
        List<Duck_User> result = entityManager.createQuery("select u from Duck_User u where email = :email AND password = :password", Duck_User.class)
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword()).getResultList();
        return result.stream().findAny();
    }

}
