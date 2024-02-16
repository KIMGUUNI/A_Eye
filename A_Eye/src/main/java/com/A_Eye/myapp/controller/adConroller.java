package com.A_Eye.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.adMapper;
import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.userVO;

@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class adConroller {

	@Autowired
	private adMapper mapper;	
	
	@PostMapping("/api/getUserAds")
	public List<adVO> getUserAds(@RequestBody userVO uservo) {
		
		System.out.println(uservo);
		List<adVO> advo = mapper.getUserAds(uservo);
		System.out.println(advo);
		return advo;		
	}
	@PostMapping("/api/Advertising")
	public List<adVO> Advertising(){
		System.out.println("Advertising 통신 완료");
		List<adVO> result= mapper.Advertising();
		
		return result;
	}
	
	
}
