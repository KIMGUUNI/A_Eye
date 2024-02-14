package com.A_Eye.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.adVO;
@Mapper
public interface videoMapper {

	List<adVO> SelectVd();


	int Approval(adVO vo);


	int Refuse(adVO vo);

}
