package hello.hellospring5;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.hellospring5.Repository.JdbcMemberRepository;
import hello.hellospring5.Repository.MemberRepository;
import hello.hellospring5.Repository.MemoryMemberRepository;
import hello.hellospring5.Service.MemberService;

@Configuration
public class Config {

	
	private final DataSource dataSource;

	@Autowired
	public Config(DataSource dataSource) {
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
