package univa.univa2.forum.service;

import org.aspectj.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;
import univa.univa2.forum.domain.ForumUser;
import univa.univa2.forum.dto.ForumUserDTO;
import univa.univa2.forum.repository.ForumRepository;
import univa.univa2.forum.utils.StringUtil;

import java.io.File;

public class ForumService {

    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public String userSignup(ForumUserDTO userDto) {
        ForumUser mUser = new ForumUser();
        mUser.setUsername(userDto.getUsername());
        mUser.setPassword(userDto.getPassword());
        mUser.setNickname(userDto.getNickname());
        mUser.setEmail(userDto.getEmail());
        /*if(!userDto.getFile().isEmpty()) {
            //mUser.setImage_url(userDto.getImage_url());
            mUser.setImage_url(imageFileWrite(userDto.getFile()));
        }*/
        mUser.setNation(userDto.getNation());
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

}
