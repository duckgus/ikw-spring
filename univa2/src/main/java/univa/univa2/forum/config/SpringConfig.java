package univa.univa2.forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import univa.univa2.forum.repository.ForumRepository;
import univa.univa2.forum.repository.JpaUserRepository;
import univa.univa2.forum.service.ForumService;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private final EntityManager entityManager;

    public SpringConfig(DataSource dataSource, EntityManager entityManager) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
    }

    @Bean
    public ForumService forumService() {
        return new ForumService(forumRepository());
    }
    @Bean
    public ForumRepository forumRepository() {
        return new JpaUserRepository(entityManager);
    }
}
