package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userVO {
   
	 // 사용자 번호 
    private int user_idx;
    
    // 사용자 이름 
    private String user_name;

    // 사용자 이메일 
    private String user_email;

    // 사용자 비밀번호 
    private String user_pw;

    // 사용자 가입날자 
    private String user_sign_date;

    // 사용자 position 
    private String user_position;
}