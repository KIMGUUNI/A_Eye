package com.A_Eye.myapp.prop;

import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.Decoders;
import lombok.Data;


// properties에 있는 key를 자바로 바꿔서 쓰겠다
@Data
@Component
@ConfigurationProperties("com.joeun.jwt") 
public class JwtProp {
	
	private String secretKey;

	public byte[] getSecretKeyBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
