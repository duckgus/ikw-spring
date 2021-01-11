package com.univa.service;

import com.univa.domain.UnivaUser;
import com.univa.dto.univaUserDTO;
import com.univa.repository.UnivaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;

public class UnivaService {
    private final UnivaRepository univaRepository;

    public UnivaService(UnivaRepository univaRepository) {
        this.univaRepository = univaRepository;
    }
    public String userSignUp(univaUserDTO userDto) {
        UnivaUser mUser = new UnivaUser();
        mUser.setUsername(userDto.getUsername());
        mUser.setPassword(userDto.getPassword());
        mUser.setNickname(userDto.getNickname());
        mUser.setEmail(userDto.getEmail());
        mUser.setNation(userDto.getNation());
        return "ok";
    }

    public Optional<univaUserDTO> userSignin(univaUserDTO userDto) {
        UnivaUser mUser = new UnivaUser();

        Optional<UnivaUser> foundUser = UnivaRepository.findUserByUsernameAndPassword(mUser);
        if(foundUser.isPresent()){
            UnivaUser user = foundUser.get();
            univaUserDTO returnUserDto = new univaUserDTO();

            returnUserDto.setIdx(user.getIdx());
            returnUserDto.setUsername(user.getUsername());
            returnUserDto.setNickname(user.getNickname());
            returnUserDto.setEmail(user.getEmail());
            returnUserDto.setNation(user.getNation());
            return Optional.ofNullable(returnUserDto);
        }  else{
            return Optional.ofNullable(null);
        }


    }
}
