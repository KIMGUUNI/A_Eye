package com.A_Eye.myapp.controller;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.adMapper;
import com.A_Eye.myapp.model.adVO;
import com.A_Eye.myapp.model.userVO;

@Transactional
@RestController
public class adConroller {

	@Autowired
	private adMapper mapper;	
	
	@PostMapping("/api/getUserAds")
	public List<adVO> getUserAds(@RequestBody userVO uservo) {
		
		System.out.println(uservo);
		List<adVO> advo = mapper.getUserAds(uservo);
		System.out.println(advo);
		return advo;		
	}
	@PostMapping("/api/Advertising")
	public List<adVO> Advertising(){
		System.out.println("Advertising 통신 완료");
		List<adVO> result= mapper.Advertising();
		
		return result;
	}
	
	@PostMapping("/api/adList")
	public List<adVO> adBoard(@RequestBody adVO vo) {
		System.out.println("드옴");
		System.out.println(vo);
		List<adVO> result = mapper.adBoard(vo);
		System.out.println(vo);
		return result;
		
	}
	 
	 @PostMapping("/api/s3Url")
	    public ResponseEntity<String> receiveS3Url(@RequestBody String url) {
	        try {
	            System.out.println("Received S3 URL: " + url);

	            // URL 디코딩
	            String decodedUrl = URLDecoder.decode(url, "UTF-8");

	            // URL을 기반으로 재생 횟수 증가
	            increasePlayCount(decodedUrl);

	            return ResponseEntity.ok("Received S3 URL successfully.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error receiving S3 URL");
	        }
	    }

	    private void increasePlayCount(String decodedUrl) {
	        try {
	            List<adVO> allAd = mapper.Advertising();

	            for (adVO vo : allAd) {
	                String dbFileName = vo.getAd_name(); 
	                if (decodedUrl.contains(dbFileName)) {
	                	System.out.println("디비 파일명"+dbFileName);
	                    int playCount = vo.getAd_play_number();
	                    vo.setAd_play_number(playCount + 1); 
	                    System.out.println(vo);
	                    mapper.updatePlayCount(vo);
	                    break; 
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @PostMapping("/api/Addata")
	    public List<adVO> Addata(@RequestBody userVO vo) {
	       List<adVO> result = mapper.Addata(vo);
	       
	        return result;
	    }
	
	
}
