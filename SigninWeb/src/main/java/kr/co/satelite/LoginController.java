package kr.co.satelite;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/login")
	public String login(String id, String pw, boolean rememberId, 
						HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("id : " + id);
		System.out.println("pw : " + pw);
		System.out.println("rememberId : " + rememberId);
		
		//1. id와 pw를 확인
		if(!logincheck(id, pw)) {
			//2-1. 일치하지 않으면, loginForm으로 이동
			String msg = URLEncoder.encode("id 또는 pw가 일치하지 않습니다.", "UTF-8");
			return "redirect:/login/login?msg="+msg;
		}
		//2-2. 일치하면 로그인 후 화면(홈화면)으로 이동
		
		//2-2-1. 쿠키를 생성
		//2-2-2. 응답헤더에 저장
		//2-3-1. 쿠키를 삭제
		//2-3-2. 응답헤더에 저장
		if(rememberId) {
			Cookie cookie = new Cookie("id", id);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("id", id);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		return "redirect:/";
	}

	private boolean logincheck(String id, String pw) {
		// TODO Auto-generated method stub
		return "ezen".equals(id) && "0111".equals(pw);
		
	}
}