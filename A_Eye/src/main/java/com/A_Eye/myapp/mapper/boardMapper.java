package com.A_Eye.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.boardVO;


@Mapper
public interface boardMapper {
	
	void writeBoard(boardVO vo);

	List<boardVO> boardList();

	List<boardVO> boardGet(String inquiry_indx);
	
	int boardAnswer(boardVO vo);
}
