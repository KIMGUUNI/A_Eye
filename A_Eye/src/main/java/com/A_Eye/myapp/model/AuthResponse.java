package com.A_Eye.myapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
public class AuthResponse {
    private String jwt;
    private String rJwt;
    private int user_idx;
    private String user_name;
    private String user_email;
    private String user_position;

    public AuthResponse(String jwt, String rJwt, int user_idx, String user_name, String user_email, String user_position) {
        this.jwt = jwt;
        this.rJwt = rJwt;
        this.user_idx = user_idx;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_position = user_position;
    }

}
