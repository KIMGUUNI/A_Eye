package com.A_Eye.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class exposureVO {
	
	// 노출 번호 
    private int expoIdx;

    // 광고 번호 
    private int adIdx;

    // 노출 날짜 
    private String expoDate;

    // 노출 연령 
    private String expoAge;

    // 노출 성별 
    private String expoGender;

    // 노출 지역 
    private String expoRegion;
    
    //월별 값
    private String month;
    
    //월별 키
    private String month_count;
    
    private int region_count;
    
    private int day_count;
    
    private String day;
    
 

}
