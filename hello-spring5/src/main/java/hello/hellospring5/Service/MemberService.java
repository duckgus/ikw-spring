package hello.hellospring5.Service;

import hello.hellospring5.Repository.MemberRepository;
import hello.hellospring5.Repository.MemoryMemberRepository;
import hello.hellospring5.domain.Member;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class MemberService {

	private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
    public List<Member> findMembers(){
                   return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
	
}
