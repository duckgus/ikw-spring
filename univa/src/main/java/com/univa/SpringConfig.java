package com.univa;

import com.univa.repository.UnivaRepository;
import com.univa.service.UnivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UnivaService forumService() {
        return new UnivaService(univaRepository());
    }

    @Bean
    public UnivaRepository univaRepository() {
        return new UnivaRepository(em);
    }
}