package com.A_Eye.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.A_Eye.myapp.model.refreshTkVO;

@Mapper
public interface refreshMapper {
    
    int refreshJwt(refreshTkVO refreshvo);
    
    refreshTkVO proveRefresh(String refreshToken);
    
    int delRefreshJwt(int userIdx);
    
    int reCreateRefreshToken(String newRefreshToken, int userIdx);
}