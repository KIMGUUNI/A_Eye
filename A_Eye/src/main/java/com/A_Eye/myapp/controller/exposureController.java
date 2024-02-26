package com.A_Eye.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.exposureMapper;
import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.exposureVO;
import com.A_Eye.myapp.model.userVO;

@Transactional
@RestController
public class exposureController {
	
	@Autowired
	exposureMapper mapper;
	
	@PostMapping("/api/getMonthAds")
	public List<exposureVO> getMonthAd(@RequestBody adVO advo) {
		System.out.println("=============================");
		System.out.println(advo);
		List<exposureVO> exvo = mapper.getMonthAd(advo);
		System.out.println(exvo);
		return exvo;
	}
	
	
	@PostMapping("/api/getChartAd")
	public List<exposureVO> getChartAd(@RequestBody adVO advo) {
		
		System.out.println(advo);
		List<exposureVO> exvo = mapper.getChartAd(advo);
		System.out.println(advo);
		return exvo;		
	}
	
	@PostMapping("/api/getBarChartAd")
	public List<exposureVO> getBarChartAd(@RequestBody adVO advo) {
		
		System.out.println(advo);
		List<exposureVO> exvo = mapper.getBarChartAd(advo);
		System.out.println(advo);
		return exvo;		
	}

}
