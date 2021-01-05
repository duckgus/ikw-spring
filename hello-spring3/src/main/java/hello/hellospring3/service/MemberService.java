package hello.hellospring3.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring3.domain.Member;
import hello.hellospring3.repository.MemberRepository;

//@Service
//@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //ȸ������
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
    public List<Member> fineMembers(){
                   return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
}
