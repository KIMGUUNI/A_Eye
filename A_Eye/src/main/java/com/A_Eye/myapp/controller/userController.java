package com.A_Eye.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.userMapper;
import com.A_Eye.myapp.model.userVO;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class userController {
   @Autowired
   private userMapper mapper;

   @PostMapping("/api/register")
   public ResponseEntity<String> registerUser(@RequestBody userVO vo) {
      System.out.println(vo);
      System.out.println("리액트 통신");

      String redirectUrl = "http://localhost:3000/";
      return ResponseEntity.status(HttpStatus.OK).header("Location", redirectUrl)
            .body("{\"message\": \"User registration successful!\"}");
   }
}