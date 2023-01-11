package kr.co.work.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Transactional;

public class AopMain {

	public static void main(String[] args) throws Exception {
		MyAdvice myAdvice = new MyAdvice();
		
		Class myClass = Class.forName("kr.co.work.aop.MyClass");
		Object obj = myClass.newInstance();
		
		for(Method m : myClass.getDeclaredMethods()) {
			myAdvice.invoke(m, obj, null);
		}
	}
}

//핵심 기능(메서드)에 공통 코드를 추가해야 함
class MyClass {
	
	@Transactional						//@Transactional이 설정된 핵심기능에만 부가기능을 추가하기
	void ezen1() {
		
		System.out.println("ezen1() is called");
	}
	
	void ezen2() {
		System.out.println("ezen2() is called");
	}
	
	void itezen1() {
		System.out.println("itezen1() is called");
	}
	
}

//보조기능
class MyAdvice {
	
	Pattern p = Pattern.compile("e.*");		//메서드 이름이 e로 시작하는 것만 골라서 적용
	
	boolean matches(Method m) {
		Matcher matcher = p.matcher(m.getName());		//패턴에 맞는 것만 취해서 그 핵심기능에만 보조기능이 들어가게 됨
		return matcher.matches();
	}
	
	void invoke(Method m, Object obj, Object...args) throws Exception {
		//if(matches(m))
		//핵심기능에 Transactional 어노테이션이 설정되어 있는 경우에만 before를 출력
		if(m.getAnnotation(Transactional.class) != null)
			System.out.println("[이전 before] { ");
		
		m.invoke(obj, args);						//ezen1(), ezen2(), itezen1() 호출가능
		
		//if(matches(m))
		if(m.getAnnotation(Transactional.class) != null)
			System.out.println("} [이전 after]");
		
	}
}


