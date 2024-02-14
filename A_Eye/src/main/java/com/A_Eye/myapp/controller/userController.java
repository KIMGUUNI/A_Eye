package com.A_Eye.myapp.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.model.AuthResponse;
import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.userVO;
import com.A_Eye.myapp.prop.JwtProp;
import com.A_Eye.myapp.provider.JwtProvider;
import com.amazonaws.client.builder.AdvancedConfig.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin(origins = "http://localhost:3000")
public class userController {
   @Autowired
   private userMapper mapper;

   @Autowired
   private JwtProp jwtProp;
   byte[] secureRandomKey;
   byte[] sigingKey;

   @Autowired
   private PasswordEncoder  passwordEncoder;

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
      String roles;
      userVO loginVO = mapper.login(vo);
      if(passwordEncoder.matches(vo.getUser_pw(), loginVO.getUser_pw())) {
      }
      if (loginVO != null && passwordEncoder.matches(vo.getUser_pw(), loginVO.getUser_pw())) {
         if (loginVO.getUser_email().equals("admin")) {
            // roles.add("1");
             roles = "1";
         } else {
             roles = "0";

         }

         sigingKey = jwtProp.getSecretKey().getBytes();
         // 토큰 생성
         String jwt = Jwts.builder().signWith(Keys.hmacShaKeyFor(sigingKey), Jwts.SIG.HS512) // 시그니처 사용할 비밀키, 알고리즘 설정
               .header() // 헤더 설정
               .add("typ", JwtProvider.TOKEN_TYPE) // typ: JWT
               .and().expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 토큰 만료 시간 설정 ()
               .claim("uid", loginVO.getUser_email()) // PAYLOAD -uid : user
               .claim("rol", roles) // PAYLOAD - rol : [ROLE_USER, ROLE_ADMIN] (권한 정보)
               .compact();
         
         AuthResponse response = new AuthResponse(jwt, loginVO.getUser_idx(), loginVO.getUser_name(), loginVO.getUser_email() );
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
      try {
         String jwt = header.replace(JwtProvider.TOKEN_PREFIX, "");
         System.out.println("검증 토큰 완료");

         // 토큰을 해석하여 Claims(페이로드에 담긴 정보)를 가져옴
         Jws<Claims> parsedToken = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(sigingKey)).build()
               .parseSignedClaims(jwt);

         String username = parsedToken.getPayload().get("uid").toString(); // 사용자 이름 추출

         Claims claims = parsedToken.getPayload();
         Object roles = claims.get("rol"); // 역할 추출

         // 역할에 따라 응답을 반환
         System.out.println(roles);
         if (roles != null) {
            if (roles.equals("1")) {
               // "1" 역할에 대한 처리
               return new ResponseEntity<String>("1", HttpStatus.OK);
            } else {
               // 다른 역할에 대한 처리
               return new ResponseEntity<String>("0", HttpStatus.OK);
            }
         } else {
            // 역할이 없는 경우에 대한 처리
            return new ResponseEntity<String>("사용자의 역할이 없습니다.", HttpStatus.BAD_REQUEST);
         }
      } catch (JwtException e) {
         // JWT 검증 실패 시 예외 처리
         return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
      }
   }
}