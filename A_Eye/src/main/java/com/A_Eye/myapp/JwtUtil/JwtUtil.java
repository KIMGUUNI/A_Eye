package com.A_Eye.myapp.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import com.A_Eye.myapp.mapper.refreshMapper;
import com.A_Eye.myapp.model.refreshTkVO;
import com.A_Eye.myapp.prop.JwtProp;
import com.A_Eye.myapp.provider.JwtProvider;

@Configuration
public class JwtUtil {
   @Autowired
   private JwtProp jwtProp;
   byte[] secureRandomKey;
   byte[] sigingKey;
   String username;
   Object roles;
   @Autowired
   private refreshMapper reMapper;


   public String generateToken(String userEmail, String role) {
      sigingKey = jwtProp.getSecretKey().getBytes();
      return Jwts.builder().signWith(Keys.hmacShaKeyFor(sigingKey), Jwts.SIG.HS512) // 시그니처 사용할 비밀키, 알고리즘 설정
            .header() // 헤더 설정
            .add("typ", JwtProvider.TOKEN_TYPE) // typ: JWT
            .and().expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 토큰 만료 시간 설정 ()
            .claim("uid", userEmail) // PAYLOAD -uid : user
            .claim("rol", role) // PAYLOAD - rol : [ROLE_USER, ROLE_ADMIN] (권한 정보)
            .compact();
   }

   public String createRefreshToken() {
      sigingKey = jwtProp.getSecretKey().getBytes();
      return Jwts.builder().signWith(Keys.hmacShaKeyFor(sigingKey), Jwts.SIG.HS512) // 시그니처 사용할 비밀키, 알고리즘 설정
            .header() // 헤더 설정
            .add("typ", JwtProvider.TOKEN_TYPE) // typ: JWT
            .and().expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 토큰 만료 시간 설정 ()
            .compact();
   }
   
   
   // 토큰 검증
   public ResponseEntity<?> userINfo(@RequestHeader(name = "Authorization") String header) {
	   System.out.println(header);
      try {
         String jwt = header.replace(JwtProvider.TOKEN_PREFIX, "");
         System.out.println("검증 토큰 완료");
         
         // access token 검증 (db)
         
         

         // 토큰을 해석하여 Claims(페이로드에 담긴 정보)를 가져옴
         Jws<Claims> parsedToken = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(sigingKey)).build()
               .parseSignedClaims(jwt);

          username = parsedToken.getPayload().get("uid").toString(); // 사용자 이름 추출

         Claims claims = parsedToken.getPayload();
          roles = claims.get("rol"); // 역할 추출

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
      } catch (ExpiredJwtException e) {
         // 토큰이 만료된 경우 처리
         return new ResponseEntity<String>("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
      } catch (SignatureException e) {
         // 토큰의 서명이 유효하지 않은 경우 처리
         return new ResponseEntity<String>("토큰의 서명이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
      } catch (JwtException e) {
         // 기타 JWT 검증 실패 시 예외 처리
         return new ResponseEntity<String>("토큰 검증에 실패했습니다.", HttpStatus.UNAUTHORIZED);
      }
   }
   
   
   
   public ResponseEntity<?> refreshToken(@RequestHeader("refreshToken") String refreshToken, String user_name, String userPosition) {
       try {
          System.out.println("===============================================토큰 재발급===============================================");
          refreshToken = refreshToken.replace(JwtProvider.TOKEN_PREFIX, "");
          refreshTkVO refresh = reMapper.proveRefresh(refreshToken);
          int user_idx = refresh.getUser_idx();
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
             LocalDateTime expiration = LocalDateTime.parse(refresh.getExpiration(), formatter);
             LocalDateTime now = LocalDateTime.now();
             
             if (expiration.isBefore(now)) {
                  System.out.println("만료 시간이 현재 시간보다 이전입니다.");
                  reMapper.delRefreshJwt(refreshToken);
                  return new ResponseEntity<String>("다시 로그인 해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
                 
              } else {
                  System.out.println("만료 시간이 현재 시간 이후입니다.");
                  System.out.println("===============check1===========");
                  String newAccessToken = generateNewAccessToken(user_name, userPosition);
                  String newRefreshToken = createRefreshToken();
                  System.out.println("===============check2===========");
                  System.out.println(newRefreshToken);
                  System.out.println(user_idx);
                  reMapper.reCreateRefreshToken(newRefreshToken, user_idx);
                  System.out.println("===============check3===========");
                  return new ResponseEntity<String>(newAccessToken, HttpStatus.OK);
              }
         
       } catch (Exception e) {
           // refresh 토큰이 유효하지 않은 경우나 발급 실패 등의 예외 처리
           return new ResponseEntity<String>("다시 로그인 해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   // 새로운 access 토큰을 반환
   private String generateNewAccessToken(String username, Object roles) {
      sigingKey = jwtProp.getSecretKey().getBytes();
      return Jwts.builder().signWith(Keys.hmacShaKeyFor(sigingKey), Jwts.SIG.HS512) // 시그니처 사용할 비밀키, 알고리즘 설정
            .header() // 헤더 설정
            .add("typ", JwtProvider.TOKEN_TYPE) // typ: JWT
            .and().expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 토큰 만료 시간 설정 ()
            .claim("uid", username) // PAYLOAD -uid : user
            .claim("rol", roles) // PAYLOAD - rol : [ROLE_USER, ROLE_ADMIN] (권한 정보)
            .compact();
   }
}