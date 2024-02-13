package com.A_Eye.myapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
public class AuthResponse {
    private String jwt;
    private int user_idx;
    private String user_name;
    private String user_email;

    public AuthResponse(String jwt, int i, String name, String email) {
        this.jwt = jwt;
        this.user_idx = i;
        this.user_name=name;
        this.user_email=email;
    }

}
