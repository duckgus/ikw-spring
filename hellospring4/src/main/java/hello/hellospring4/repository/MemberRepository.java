package hello.hellospring4.repository;

import java.util.*;

import hello.hellospring4.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
