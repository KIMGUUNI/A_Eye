package com.A_Eye.myapp.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.model.AuthResponse;
import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.refreshTkVO;
import com.A_Eye.myapp.model.userVO;
import com.A_Eye.myapp.model.refreshTkVO;
import com.A_Eye.myapp.prop.JwtProp;
import com.A_Eye.myapp.provider.JwtProvider;
import com.amazonaws.client.builder.AdvancedConfig.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.JwtUtil.JwtUtil;
import com.A_Eye.myapp.mapper.refreshMapper;
import com.A_Eye.myapp.domain.AuthenticationRequest;
import com.A_Eye.myapp.mapper.userMapper;
import com.A_Eye.myapp.model.userVO;
import com.A_Eye.myapp.prop.JwtProp;
import com.A_Eye.myapp.provider.JwtProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Transactional
@RestController
public class userController {
	@Autowired
	private userMapper mapper;

	@Autowired
	private JwtProp jwtProp;
	// byte[] secureRandomKey;
	byte[] sigingKey;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private refreshMapper reMapper;

	@PostMapping("/api/register")
	public ResponseEntity<String> registerUser(@RequestBody userVO vo) {
		vo.incode(vo.getUser_pw());
		mapper.register(vo);

		String redirectUrl = "http://localhost:3000/authentication/sign-in/";
		return ResponseEntity.status(HttpStatus.OK).header("Location", redirectUrl)
				.body("{\"message\": \"User registration successful!\"}");
	}

	@PostMapping("/api/sign-in")
	public ResponseEntity<AuthResponse> login(@RequestBody userVO vo) {
		refreshTkVO refreshvo = new refreshTkVO();
		String roles;
		userVO loginVO = mapper.login(vo);
		if (loginVO != null && passwordEncoder.matches(vo.getUser_pw(), loginVO.getUser_pw())) {
			if (loginVO.getUser_email().equals("admin")) {
				// roles.add("1");
				roles = "1";
			} else {
				roles = "0";
			}

			String accessJwt = jwtUtil.generateToken(loginVO.getUser_email(), roles);
			String refreshJwt = jwtUtil.createRefreshToken();
			
			System.out.println(accessJwt);
			System.out.println(refreshJwt);
			
			refreshvo.setUser_idx(loginVO.getUser_idx());
			refreshvo.setJwt_refresh(refreshJwt);
			reMapper.refreshJwt(refreshvo);
			
			// db에 refreshJwt 저장
			// 저장하면 1시간뒤에 삭제해야댐 (user_idx)를 같이 보내줘야 한다...
			// access 토큰이 만료되었을 때 refreshJwt를 불러와서 검증
			// 검증되었다면 access 토큰 재발급
			// refreshJwt가 없다면 재로그인
			AuthResponse response = new AuthResponse(accessJwt, refreshJwt,  loginVO.getUser_idx(), loginVO.getUser_name(),
					loginVO.getUser_email(), loginVO.getUser_position());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}

	@PostMapping("/api/application")
	public void application(@RequestBody adVO vo) {
		mapper.application(vo);
		System.out.println(vo);
	}

	@GetMapping("/api/prove")
	public ResponseEntity<?> userINfo(@RequestHeader(name = "Authorization") String header) {
		return jwtUtil.userINfo(header);
	}
	
	
	@GetMapping("/api/reProve")
	public ResponseEntity<?> refreshToken(@RequestHeader(name = "Authorization") String header,
	                                       @RequestParam(name = "user_name") String user_name,
	                                       @RequestParam(name = "user_position") String userPosition,
	                                       @RequestParam(name = "user_idx") String user_idx) {
	    return jwtUtil.refreshToken(header, user_name, userPosition, user_idx);
	}
	
}