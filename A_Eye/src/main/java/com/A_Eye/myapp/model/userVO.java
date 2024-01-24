package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userVO {
   
	 // 사용자 번호 
    private int userIdx;
    
    // 사용자 이름 
    private String userName;

    // 사용자 이메일 
    private String userEmail;

    // 사용자 비밀번호 
    private String userPw;

    // 사용자 가입날자 
    private String userSignDate;

    // 사용자 position 
    private String userPosition;
}