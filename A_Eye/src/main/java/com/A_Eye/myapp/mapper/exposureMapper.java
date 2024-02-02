package com.A_Eye.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.exposureVO;

@Mapper
public interface exposureMapper {

	List<exposureVO> getMonthAd(adVO idx);

	List<exposureVO> getChartAd(adVO advo);

	List<exposureVO> getBarChartAd(adVO advo);

}
