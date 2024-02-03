package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter
@AllArgsConstructor // 필드 생성자
@NoArgsConstructor // 기본 생성자
public class boardVO {
	
	private int inquiry_indx;
	
	private int user_idx;
	
	private String inquiry_content;
	
	private int inquiry_completed;
	
	private String answer_content;
	
	private String inquiry_title;
	
	private String inquiry_pw;
	
	private String inquiry_date;
	
}
