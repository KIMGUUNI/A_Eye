package com.A_Eye.myapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.A_Eye.myapp.mapper.videoMapper;
import com.A_Eye.myapp.model.adVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

@RestController
public class videoController {

	@Autowired
	private AmazonS3 amazonS3Client;

	@Autowired
	private videoMapper mapper;

	@PostMapping("/api/SelectVd")
	public List<adVO> SelectVd() {
		List<adVO> allAd = mapper.SelectVd();
		System.out.println("통신");
		return allAd;

	}

	@PostMapping("/api/GetVideoUrl")
	public ResponseEntity<String> getVideoUrl(@RequestBody adVO vo) {
		try {
			System.out.println(vo);
			String ad_name = vo.getAd_name();
			String age = vo.getAd_target_age();
			String gender = vo.getAd_target_gender();

			URL videoUrl = generatePresignedUrl(ad_name, age, gender);
			System.out.println("s3 URL : " + videoUrl);
			return ResponseEntity.ok(videoUrl.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching video URL");
		}
	}

	private URL generatePresignedUrl(String ad_name, String age, String gender) {
		String bucketName = "video-add";
		String objectKey = age + "/" + gender + "/" + ad_name;
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				objectKey);
		generatePresignedUrlRequest.setMethod(com.amazonaws.HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis() + 36000));
		return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
	}

	@PostMapping("/api/Approval")
	public int Approval(@RequestBody adVO vo) {
	    try {
	        System.out.println(vo);
	        int result = mapper.Approval(vo);
	        System.out.println("업데이트 결과: " + result);
	        if (result > 0) {
	            // S3에서 가져온 기존 URL을 새로운 폴더에 복사
	            String oldUrl = vo.getFile_s3_path();
	            System.out.println("원래 URL : "+oldUrl);
	            String newUrl = copyObjectToNewFolder(oldUrl, vo);
	            System.out.println("새로운 URL: " + newUrl);
	            return result;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	private String copyObjectToNewFolder(String oldUrl, adVO vo) {
		String ad_name = vo.getAd_name();
		String age = vo.getAd_target_age();
		String gender = vo.getAd_target_gender();
	    String objectKey = age+"/"+gender+"/"+ad_name; // 객체 키
	    
	    System.out.println("객체키 :"+objectKey);

	    // 새로운 폴더 경로 생성 (예: "new_folder/age/gender/ad_name")
	    String newFolderPath = "new_folder/" + age + "/" + gender + "/" + ad_name;
	    System.out.println("새로운 URL: " + newFolderPath);

	    // S3 객체를 새로운 폴더로 복사
	    amazonS3Client.copyObject("video-add", objectKey, "video-add", newFolderPath);

	    // 복사된 객체의 URL을 인코딩하여 반환
	    return encodeUrl("https://video-add.s3.ap-northeast-2.amazonaws.com/" + newFolderPath);
	}

	private String encodeUrl(String url) {
	    try {
	        return java.net.URLEncoder.encode(url, java.nio.charset.StandardCharsets.UTF_8.toString());
	    } catch (java.io.UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@PostMapping("/api/Refuse")
	public int Refuse(@RequestBody adVO vo) {
		int result = mapper.Refuse(vo);		
		return result;
		
	}
	
	

}