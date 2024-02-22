package com.A_Eye.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.userVO;
	
@Mapper
public interface adMapper {

	List<adVO> getUserAds(userVO vo);

	List<adVO> Advertising();

	void updatePlayCount(adVO vo);
	
	List<adVO> adBoard(adVO vo);

	List<adVO> Addata(userVO vo);
	
		
}
