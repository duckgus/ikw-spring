package hello.hellospring5.Repository;

import hello.hellospring5.domain.Member;

import java.util.*;

public interface MemberRepository {
	
	Member save(Member member);
	Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
