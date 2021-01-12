package univa.forum.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import univa.forum.dto.ForumUserDTO;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession httpSession = request.getSession();
		ForumUserDTO user = (ForumUserDTO) httpSession.getAttribute("ForumUserSession");
		
//		System.out.println("url path? "+request.getServletPath());
		if(user == null) {
			response.sendRedirect("/widget/warning.html");
			//throw new Exception("로그인 필요!");
			
//			response.setContentType("text/html");
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write("<html><script>alert('로그인이 필요합니다.'); location.href='/forum/signin';</script></html>");
			return false;
		}
		httpSession.setMaxInactiveInterval(60*60);
		return true;
	}
	
	public void preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// controller 후 처리
	}
}
