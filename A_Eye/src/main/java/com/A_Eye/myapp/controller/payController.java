package com.A_Eye.myapp.controller;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.adMapper;
import com.A_Eye.myapp.mapper.payMapper;
import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.payVO;
import com.A_Eye.myapp.model.userVO;

@Transactional
@RestController
public class payController {

	@Autowired
	private payMapper mapper;	
	
	@PostMapping("/api/payResult")
	public int payResult(@RequestBody payVO payvo) {
		System.out.println(payvo);
		int a = mapper.payResult(payvo);
		System.out.println(payvo);
		return a;		
	}
	
	
	
}
