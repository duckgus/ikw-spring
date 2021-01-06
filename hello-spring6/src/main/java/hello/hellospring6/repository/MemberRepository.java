package hello.hellospring6.repository;

import hello.hellospring6.domain.Member;

import java.util.*;

public interface MemberRepository {

	Member save(Member member);
	Optional<Member> findById(Long id);
	Optional<Member> findByName(String name);
	List<Member> findAll();
	
}
