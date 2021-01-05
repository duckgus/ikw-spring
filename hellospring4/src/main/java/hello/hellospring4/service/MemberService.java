package hello.hellospring4.service;

import hello.hellospring4.domain.Member;
import hello.hellospring4.repository.MemberRepository;

import java.util.*;

public class MemberService {

	private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public long join(Member member) {
        //같은 이름이 있는 중복 회원x
            validateDuplicateMember(member);//중복회원검증
            memberRepository.save(member);
            return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    //전체 회원 조회
    public List<Member> fineMembers(){
                   return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
}
