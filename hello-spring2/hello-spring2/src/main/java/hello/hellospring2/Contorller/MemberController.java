package hello.hellospring2.Contorller;

import org.springframework.beans.factory.annotation.Autowired;

import hello.hellospring2.Service.MemberService;

public class MemberController {
	
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
	this.memberService = memberService;
	}
}
