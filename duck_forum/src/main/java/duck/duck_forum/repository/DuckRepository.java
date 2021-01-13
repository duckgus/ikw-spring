package duck.duck_forum.repository;

import duck.duck_forum.domain.Duck_User;

import java.util.Optional;

public interface DuckRepository {
    Duck_User save(Duck_User dUser);
    Optional<Duck_User> findByUsername(String username);
    Optional<Duck_User> findUserByUsernameAndPassword(Duck_User dUser);
}
