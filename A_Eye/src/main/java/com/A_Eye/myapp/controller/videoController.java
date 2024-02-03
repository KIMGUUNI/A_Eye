package com.A_Eye.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.userMapper;

@RestController
public class videoController {
	 @Autowired
	   private userMapper mapper;
}
