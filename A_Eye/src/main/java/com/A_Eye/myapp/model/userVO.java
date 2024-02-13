package com.A_Eye.myapp.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// import com.smhrd.myapp.entity.CloudMember;

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
    
    public void incode(String user_pw) {
        this.user_pw = encryptPassword(user_pw);
    }
    
    
    private String encryptPassword(String user_pw) {
        // BCryptPasswordEncoder를 사용하여 비밀번호를 암호화
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(user_pw);
    }
}