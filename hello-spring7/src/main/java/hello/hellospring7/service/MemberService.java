package hello.hellospring7.service;

import java.util.*;

import hello.hellospring7.domain.Member;
import hello.hellospring7.repository.MemberRepository;

import javax.transaction.Transactional;

@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	//
	public Long join(Member member) {
		
		validateDuplicatMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicatMember(Member member) {
		memberRepository.findByName(member.getName())
		.ifPresent(m-> {
			throw new IllegalStateException("이미 존재하는 회원입니다");
		});
	}
	
	//
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}

	public List<Member> delete(Member member){
		return memberRepository.delete();
	}

}
