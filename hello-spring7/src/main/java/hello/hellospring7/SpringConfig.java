package hello.hellospring7;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import hello.hellospring7.repository.MemberRepository;
import hello.hellospring7.repository.testRepository;
import hello.hellospring7.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	private final DataSource dataSource;

	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository() {
		return new testRepository(dataSource);
	}

}


