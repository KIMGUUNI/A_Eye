package com.A_Eye.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.userVO;

@Mapper
public interface userMapper {

	void register(userVO vo);

}
