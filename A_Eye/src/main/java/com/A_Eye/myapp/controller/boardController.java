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

@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
	public List <boardVO> boardGet(@RequestBody String inquiry_indx){
		System.out.println(inquiry_indx);
		inquiry_indx = inquiry_indx.substring(0,2);
		System.out.println(inquiry_indx);
		List <boardVO> result = mapper.boardGet(inquiry_indx);
		return result;
	}
	
	@PostMapping("/api/boardAnswer")
	public int boardAnswer (@RequestBody boardVO vo){
		System.out.println("통신 성공");
		System.out.println(vo);
		int result = mapper.boardAnswer(vo);
		System.out.println(result);
		return result;
	}

}
