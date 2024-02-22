package com.A_Eye.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.boardMapper;
import com.A_Eye.myapp.model.boardVO;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.v3.oas.annotations.tags.Tag;

@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@Tag(name = "예제 API", description = "Swagger 테스트용 API")
public class boardController {

	@Autowired
	private boardMapper mapper;
	

	@PostMapping("/api/profile")
	public ResponseEntity<String> registerBoard(@RequestBody boardVO vo) {
		mapper.writeBoard(vo);
		String redirectUrl = "http://localhost:3000/authentication/profile/";
		return ResponseEntity.status(HttpStatus.OK).header("Location", redirectUrl)
				.body("{\"message\": \"User registration successful!\"}");
	}
	
	@PostMapping("/api/boardList")
	public List <boardVO> boardList(){
		List <boardVO> result = mapper.boardList();
		return result;
	}
	
	@PostMapping("/api/boardGet")
	public boardVO boardGet(@RequestBody boardVO vo){
		System.out.println(vo.getInquiry_indx());
		int result =  vo.getInquiry_indx();
		boardVO data= mapper.boardGet(result);
		return data;
	}
	
	@PostMapping("/api/boardAnswer")
	public int boardAnswer (@RequestBody boardVO vo){
		System.out.println("통신 성공");
		System.out.println(vo);
		int result = mapper.boardAnswer(vo);
		System.out.println(result);
		return result;
	}
	@PostMapping("/api/deletePost")
	public int deletePost(@RequestBody boardVO vo) {
		System.out.println("삭제할 데이터"+vo);
		mapper.deletePost(vo);
		return 123;
	}

}
