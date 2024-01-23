package com.A_Eye.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.userMapper;
import com.A_Eye.myapp.model.userVO;
@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class userController {
   @Autowired
   private userMapper mapper;
   
  
   @PostMapping("/api/register")
   public ResponseEntity<String> registerUser(@RequestBody userVO vo) {
      System.out.println(vo);
      System.out.println("리액트 통신");
      vo.setName(vo.getName());
      System.out.println(vo.getName());
      System.out.println(vo.getPassword());
      System.out.println(vo.getEmail());
      System.out.println(vo.getSsn());
      vo.setPassword(vo.getPassword());
      vo.setEmail(vo.getEmail());
      vo.setSsn(vo.getSsn());
      mapper.register(vo);
      String redirectUrl = "http://localhost:3000/authentication/sign-in/";
      return ResponseEntity.status(HttpStatus.OK).header("Location", redirectUrl)
            .body("{\"message\": \"User registration successful!\"}");
   }
   
   
   
   
   
   
   
   
   
   
   
  
   @PostMapping("/api/sign-in")
   public userVO login(@RequestBody userVO vo) {
	   System.out.println(vo);
	   System.out.println("리액트 통신");
	   System.out.println("리액트 통신2");
	   vo.setEmail(vo.getEmail());
	   vo.setPassword(vo.getPassword());
	  userVO loginVO = mapper.login(vo);
	  System.out.println(loginVO);
	   if(loginVO!=null) {
		   return loginVO;
	   }else {
		   return null;
	   }
   }
   
   
   
}