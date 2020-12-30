package hellospring.hellospring;

import hellospring.hellospring.repository.MemberRepository;
import hellospring.hellospring.repository.MemoryMemberRepository;
import hellospring.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
