package hello.hellospring3;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.hellospring3.repository.JdbcMemberRepository;
import hello.hellospring3.repository.MemberRepository;
import hello.hellospring3.service.MemberService;

@Configuration
public class SpringConfig {

	private DataSource dataSource;
	
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
