package univa.univa2.forum.repository;

import univa.univa2.forum.domain.ForumUser;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements ForumRepository{

    private EntityManager entityManager;
    public JpaUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ForumUser save(ForumUser user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<ForumUser> findByUsername(String username) {
        List<ForumUser> result = entityManager.createQuery("select m from user m where m.username = :username", ForumUser.class)
                .setParameter("username", username).getResultList();
        return result.stream().findAny();
    }

/*    @Override
    public Optional<ForumUser> findById(Long idx) {
        ForumUser forumUser = entityManager.find(ForumUser.class, idx);
        return Optional.ofNullable(forumUser);
    }

    @Override
    public Optional<ForumUser> findByNickname(String nickname) {
        List<ForumUser> result = entityManager.createQuery("select m from user m where m.nickname = :nickname", ForumUser.class)
                .setParameter("nickname", nickname).getResultList();
        return result.stream().findAny();
    }*/
}
