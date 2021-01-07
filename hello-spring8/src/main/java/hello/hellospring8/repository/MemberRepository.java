package hello.hellospring8.repository;

import java.util.List;
import java.util.Optional;

import hello.hellospring8.domain.Member;

public interface MemberRepository {

	Member save(Member member);
	Optional<Member> findById(Long id);
	Optional<Member> findByName(String name);
	List<Member> findAll();
	
}