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
