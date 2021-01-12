package univa.univa2.forum.repository;

import univa.univa2.forum.domain.ForumUser;

import java.util.Optional;

public interface ForumRepository {
    ForumUser save(ForumUser user);
    Optional<ForumUser> findByUsername(String username);


}
