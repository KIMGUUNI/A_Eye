package com.A_Eye.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.userVO;

@Mapper
public interface userMapper {

	void register(userVO vo);

	userVO login(userVO vo);

	void application(adVO vo);

}
