package duck.duck_forum.service;

import duck.duck_forum.domain.Duck_User;
import duck.duck_forum.dto.DuckUserDto;
import duck.duck_forum.repository.DuckRepository;
import duck.duck_forum.repository.JpaDuckRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class DuckService {
    private DuckRepository duckRepository;
    @Autowired
    public DuckService(DuckRepository duckRepository) {
        this.duckRepository = duckRepository;
    }

    public int signup(Duck_User dUser) {
        validateDuplicateMember(dUser);
        duckRepository.save(dUser);
        return dUser.getIdx();
    }
    private void validateDuplicateMember(Duck_User dUser) {
        duckRepository.findByUsername(dUser.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public Optional<DuckUserDto> Signin(DuckUserDto duckUserDto) {
        Duck_User dUser = new Duck_User();
        dUser.setUsername(duckUserDto.getUsername());
        dUser.setPassword(duckUserDto.getPassword());

        Optional<Duck_User> foundUser = duckRepository.findUserByUsernameAndPassword(dUser);
        if (foundUser.isPresent()) {
            Duck_User user = foundUser.get();
            DuckUserDto returnUserDTO = new DuckUserDto();
            returnUserDTO.setIdx(user.getIdx());
            returnUserDTO.setNickname(user.getNickname());
            returnUserDTO.setPassword(user.getPassword());
            returnUserDTO.setNickname(user.getNickname());
            returnUserDTO.setEmail(user.getEmail());
            returnUserDTO.setNation(user.getNation());
            return Optional.ofNullable(returnUserDTO);
        }else{
            return Optional.ofNullable(null);
        }
    }
}
