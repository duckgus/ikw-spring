package hello.hellospring8.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring8.domain.Member;
import hello.hellospring8.repository.MemberRepository;

public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	//ȸ������
	public Long join(Member member) {
		
		validateDuplicatMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicatMember(Member member) {
		memberRepository.findByName(member.getName())
		.ifPresent(m-> {
			throw new IllegalStateException("�̹� �����ϴ� ȸ���Դϴ�.");
		});
	}
	
	//��ü ȸ�� ��ȸ
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
}
