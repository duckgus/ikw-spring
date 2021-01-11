package univa.univa2.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import univa.univa2.forum.repository.ForumRepository;
import univa.univa2.forum.service.ForumService;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public ForumService forumService() {
        return new ForumService(forumRepository());
    }

    @Bean
    public ForumRepository forumRepository() {
        return new ForumRepository(em);
    }
}
