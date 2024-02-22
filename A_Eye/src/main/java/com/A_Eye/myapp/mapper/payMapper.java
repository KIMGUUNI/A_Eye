package com.A_Eye.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.payVO;

@Mapper
public interface payMapper {

	int payResult(payVO vo);
}
