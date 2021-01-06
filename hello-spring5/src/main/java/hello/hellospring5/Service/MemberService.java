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
        //���� �̸��� �ִ� �ߺ� ȸ��x
            validateDuplicateMember(member);//�ߺ�ȸ������
            memberRepository.save(member);
            return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("�̹� �����ϴ� ȸ���Դϴ�.");
                });
    }
    //��ü ȸ�� ��ȸ
    public List<Member> findMembers(){
                   return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
	
}
