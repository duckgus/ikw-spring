package hello.hellospring2.Contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.hellospring2.Service.MemberService;
import hello.hellospring2.domain.Member;

@Controller
public class HomeController {

	private MemberService memberService;
	
	@Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }	
    @GetMapping("/")
    public String home() { 
    	return "home"; 
    }
    
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMember";
    }
    
    @PostMapping("/members/new")
    public String create(MemberForm form) {
       Member member = new Member();
       member.setName(form.getName());
	
       memberService.join(member);
       
       return "redirect:/";
	}
	
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}