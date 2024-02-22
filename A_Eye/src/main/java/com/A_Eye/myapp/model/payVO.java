package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class payVO {
	private int pay_idx;
	private int ad_idx;
	private String pay_cost;
	private int date;
	private String pay_method;
}
