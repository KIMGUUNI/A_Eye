package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class adVO {
	private int ad_idx;
	private String ad_name;
	private int user_idx;
	private String ad_target_age;
	private String ad_target_gender;
	private char ad_approval;
	private int ad_expo_num;
}
