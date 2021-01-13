package duck.duck_forum;

import duck.duck_forum.repository.DuckRepository;
import duck.duck_forum.repository.JpaDuckRepository;
import duck.duck_forum.service.DuckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public DuckService duckService(){
        return new DuckService(duckRepository());
    }

    @Bean
    public DuckRepository duckRepository(){
        return new JpaDuckRepository(em);
    }
}
