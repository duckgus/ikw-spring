package hello.hellospring6.repository;

import javax.persistence.EntityManager;

import hello.hellospring6.domain.Member;
import java.util.*;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager entityManager;
    //jpa�� em���� ������
    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("select m from Member m", Member.class) //���̺�x ��ü�� �������query�� ����
                .getResultList();
    }
}
