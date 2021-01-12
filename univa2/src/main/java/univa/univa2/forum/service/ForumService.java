package univa.univa2.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import univa.univa2.forum.domain.ForumUser;
import univa.univa2.forum.repository.ForumRepository;

import javax.transaction.Transactional;

@Transactional
public class ForumService {

    private final ForumRepository forumRepository;
    @Autowired
    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public long SignUp(ForumUser User) {
        validateDuplicateUser(User);
        forumRepository.save(User);
        return User.getIdx();
    }

    private void validateDuplicateUser(ForumUser forumUser) {
        forumRepository.findByUsername(forumUser.getUsername())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


}
