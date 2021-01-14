package duck.duck_forum.service;

import duck.duck_forum.domain.DuckPost;
import duck.duck_forum.domain.Duck_User;
import duck.duck_forum.repository.DuckRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class DuckService {
    private final DuckRepository duckRepository;

    public DuckService(DuckRepository duckRepository) {
        this.duckRepository = duckRepository;
    }

    public int join(Duck_User user){
        validateDuplicateMember(user);
        duckRepository.save(user);
        return user.getIdx();
    }

    public void validateDuplicateMember(Duck_User user) {
        duckRepository.findByUsername(user.getUsername())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public Optional<DuckPost> login(DuckPost duckPost) {
        Duck_User duck_user = new Duck_User();
        duck_user.setUsername(duckPost.getUsername());
        duck_user.setPassword(duckPost.getPassword());

        Optional<Duck_User> foundUser = duckRepository.findByUsernameANDPassword(duck_user);
        if (foundUser.isPresent()) {
            Duck_User user = foundUser.get();
            DuckPost dto = new DuckPost();

            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            return Optional.ofNullable(dto);
        }else{
            return Optional.ofNullable(null);
        }

    }
}

