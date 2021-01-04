package hello.hellospring2.Repository;

import java.util.*;

import org.springframework.stereotype.Repository;

import hello.hellospring2.domain.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository{

	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Member save(Member member) {
		// TODO Auto-generated method stub
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}

	@Override
	 public Optional<Member> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	 }

	@Override
	public Optional findByName(String name) {
		// TODO Auto-generated method stub
		return store.values().stream()
				 .filter(member -> member.getName().equals(name))
				 .findAny();
	}

	@Override
	public List<Member> findAll() {
		// TODO Auto-generated method stub
		return new ArrayList<>(store.values());
	}
	
	public void clearStore() {
		store.clear();
	}
	

}
