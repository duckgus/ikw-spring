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
        System.out.println("Join");
        validateDuplicateMember(user);
        duckRepository.save(user);
        return user.getIdx();
    }

    public void validateDuplicateMember(Duck_User user) {
        System.out.println("중복조회");
        duckRepository.findByUsername(user.getUsername())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public Optional<Duck_User> login(Duck_User user) {

        Optional<Duck_User> foundUser = duckRepository.findByUsernameANDPassword(user);
//        return Optional.ofNullable(foundUser.get());
        if(foundUser.isPresent()){
            Duck_User duck = foundUser.get();
            duck.getIdx();
            duck.getUsername();
            duck.getPassword();
            return Optional.of(duck);
        }else{
            return Optional.ofNullable(null);
        }

    }
}

