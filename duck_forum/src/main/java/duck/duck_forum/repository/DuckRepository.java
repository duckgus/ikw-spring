package duck.duck_forum.repository;


import duck.duck_forum.domain.Duck_User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DuckRepository {
    Duck_User save(Duck_User user);
    Optional<Duck_User> findByUsername(String user);
    Optional<Duck_User> findByUsernameANDPassword(Duck_User duck);
    Optional<Duck_User> findByUseremail(String user);
    Optional<Duck_User> findByUseremailANDPassword(Duck_User duck);
}
