package univa.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import univa.forum.domain.ForumUser;
import univa.forum.dto.ForumUserDTO;
import univa.forum.repository.ForumRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class ForumService {
    private final ForumRepository forumRepository;
    @Autowired
    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }
    public String userSignup(ForumUserDTO userDto) {
        ForumUser mUser = new ForumUser();
        mUser.setUsername(userDto.getUsername());
        mUser.setPassword(userDto.getPassword());
        mUser.setNickname(userDto.getNickname());
        mUser.setEmail(userDto.getEmail());
        mUser.setNation(userDto.getNation());
        //if(true){
        if(validateDuplicateUser(mUser)) {
            if( forumRepository.save(mUser).getUsername().equals(userDto.getUsername()) ) {
                return "ok";
            } else {
                return "error";
            }
        } else {
            return "duplicaiton"; //이미 있는 유저
        }
    }
    public Boolean validateDuplicateUser(ForumUser user) {
        return !forumRepository.findUserByUsername(user.getUsername()).isPresent();
    }

    public Optional<ForumUserDTO> userSignin(ForumUserDTO userDto) {
        ForumUser mUser = new ForumUser();
        mUser.setUsername(userDto.getUsername());
        mUser.setPassword(userDto.getPassword());

        Optional<ForumUser> foundUser = forumRepository.findUserByUsernameAndPassword(mUser);
        if(foundUser.isPresent()) {
            ForumUser user = foundUser.get();
            ForumUserDTO returnUserDto = new ForumUserDTO();
            returnUserDto.setIdx(user.getIdx());
            returnUserDto.setUsername(user.getUsername());
            //returnUserDto.setPassword(user.getPassword());
            returnUserDto.setNickname(user.getNickname());
            returnUserDto.setEmail(user.getEmail());
            returnUserDto.setImage_url(user.getImage_url());
            //returnUserDto.setGrade_idx(user.getGrade().getIdx());
            returnUserDto.setNation(user.getNation());
            //returnUserDto.setState(user.getState());
            //returnUserDto.setDate(user.getDate());
            return Optional.ofNullable(returnUserDto);
        } else {
            return Optional.ofNullable(null);
        }
    }
}
