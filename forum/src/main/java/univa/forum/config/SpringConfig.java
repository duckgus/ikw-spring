package univa.forum.config;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import univa.forum.repository.ForumRepository;
import univa.forum.service.ForumService;


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
