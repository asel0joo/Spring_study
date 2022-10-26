package kr.co.ezenit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller				//1. 원격 호출가능한 프로그램 등록
public class RequestInfo {
	
	@RequestMapping("/requestInfo")			//2. URL과 메서드 연결
	public void ezen(HttpServletRequest request) {
		//요청 내용의 인코딩
		System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding()); 
		//프로토콜의 종류와 버전
		System.out.println("request.getProtocol() = " + request.getProtocol()); 
		//요청 방법
		System.out.println("request.getMethod() = " + request.getMethod()); 
	}
}
