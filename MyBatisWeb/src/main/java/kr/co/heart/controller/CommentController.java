package kr.co.heart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.heart.domain.CommentDto;
import kr.co.heart.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	CommentService service;
	
	@PatchMapping("/comments/{cno}")
	public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session){
		
		String commenter = (String) session.getAttribute("id");
		//String commenter = "ezen";
		dto.setCommenter(commenter);
		dto.setCno(cno);
		System.out.println("dto = " + dto);
		
		try {
			if(service.modify(dto) != 1)
				throw new Exception("Update failed.");
			return new ResponseEntity<String>("MOD_OK", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("MOD_OK", HttpStatus.BAD_REQUEST);
		}
	}

	//댓글을 등록하는 메서드
	@PostMapping("/comments")
	public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
		//로그인을 안하고 commenter 정보를 입력해줘야함
		String commenter = "ezen";
		
		dto.setCommenter(commenter);
		dto.setBno(bno);
		System.out.println("dto = " + dto);
		
		try {
			if(service.write(dto) !=1)
				throw new Exception("write failed");
			return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//지정된 댓글을 삭제하는 메서드 
	@DeleteMapping("/comments/{cno}")
	public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
		String commenter = "ezen";
		
		try {
			int rowCnt = service.remove(cno, bno, commenter);
			
			if(rowCnt != 1) 
				throw new Exception("Delete Failed");
			
			return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	// 지정된 게시물의 모든 댓글을 가져오는 메서드 
	@GetMapping("/comments")			// /comments?bno=308   GET
	@ResponseBody
	//public List<CommentDto> list(Integer bno) {
	public ResponseEntity<List<CommentDto>> list(Integer bno) {		
		List<CommentDto> list = null;
		
		try {
			list = service.getList(bno);
			
			System.out.println("list = " + list);
			return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);		//200
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST);	//400
		}
		
		
		//return list;
	}
	
}













