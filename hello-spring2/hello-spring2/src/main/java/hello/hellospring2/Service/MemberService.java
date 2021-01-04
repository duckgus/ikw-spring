package hello.hellospring2.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.hellospring2.Repository.MemberRepository;
import hello.hellospring2.Repository.MemoryMemberRepository;
import hello.hellospring2.domain.Member;

@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	//회원가입
	
	public Long join(Member member) {
		 validateDuplicateMember(member); //중복 회원 검증
		 memberRepository.save(member);
		 return member.getId();
		}
	private void validateDuplicateMember(Member member) {
		 memberRepository.findByName(member.getName())
		 .ifPresent(m -> {
		 throw new IllegalStateException("이미 존재하는 회원입니다.");
		 });
	}
	public List<Member> findMembers() {
		 return memberRepository.findAll();
	}
	public Optional<Member> findOne(Long memberId) {
		 return memberRepository.findById(memberId);
	}

}
