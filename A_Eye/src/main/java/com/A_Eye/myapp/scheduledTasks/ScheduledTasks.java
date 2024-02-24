package com.A_Eye.myapp.scheduledTasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.A_Eye.myapp.mapper.refreshMapper;
import com.A_Eye.myapp.model.refreshTkVO;

@Component
public class ScheduledTasks {

	@Autowired
	private refreshMapper reMapper;

	@Scheduled(fixedRate = 604800000)
	public void performTask() {
		reMapper.deleteExpiredTokens();
		
	}
}