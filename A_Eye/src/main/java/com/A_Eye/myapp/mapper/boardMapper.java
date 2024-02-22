package com.A_Eye.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.boardVO;


@Mapper
public interface boardMapper {
	
	
	void writeBoard(boardVO vo);

	List<boardVO> boardList();

	boardVO boardGet(int result);
	
	int boardAnswer(boardVO vo);

	int deletePost(boardVO vo);
}
