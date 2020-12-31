package hellospring.hellospring;

import hellospring.hellospring.aop.TimeTraceAop;
import hellospring.hellospring.repository.*;
import hellospring.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
/*    private EntityManager entityManager;
    @Autowired
    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }*/

/*private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    /*
        @Bean
        public MemberRepository memberRepository() {
    //        return new MemoryMemberRepository();
    //        return new JdbcMemberRepository(dataSource);
    //        return new JdbcTemplateMemberRepository(dataSource);
    //        return new JpaMemberRepository(entityManager);
        }
    */
/*    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }*/

}
