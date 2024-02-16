package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter
@AllArgsConstructor // 필드 생성자
@NoArgsConstructor // 기본 생성자
public class refreshTkVO {

	private int jwt_idx;
	
	private int user_idx;
	
	private String jwt_refresh;
	
	private String expiration;
	
}
