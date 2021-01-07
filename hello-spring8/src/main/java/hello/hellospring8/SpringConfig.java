package hello.hellospring8;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.hellospring8.repository.JdbcMemberRepository;
import hello.hellospring8.repository.MemberRepository;
import hello.hellospring8.repository.MemoryMemberRepository;
import hello.hellospring8.service.MemberService;

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
		//return new MemoryMemberRepository();
		return new JdbcMemberRepository(dataSource);
	}
}
