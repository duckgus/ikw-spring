package hello.hellospring6;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.hellospring6.repository.JpaMemberRepository;
import hello.hellospring6.repository.MemberRepository;
import hello.hellospring6.repository.MemoryMemberRepository;
import hello.hellospring6.service.MemberService;

@Configuration
public class SpringConfig {
	
	private final DataSource dataSource;
	private final EntityManager em;
	
	
//	public SpringConfig(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
	
	
	public SpringConfig(DataSource dataSource, EntityManager em) {
		this.dataSource = dataSource;
		this.em = em;
	}
	
	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}
	
	@Bean
	public MemberRepository memberRepository() {
//		return new MemoryMemberRepository();
//		return new JdbcMemberRepository(dataSource);
//		return new JdbcTemplateMemberRepository(dataSource);
		return new JpaMemberRepository(em);
	}
}
