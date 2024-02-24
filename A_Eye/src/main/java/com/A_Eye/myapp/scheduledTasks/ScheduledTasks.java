package com.A_Eye.myapp.scheduledTasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    // 1시간마다 실행되도록 스케줄링합니다.
    //@Scheduled(fixedRate = 3600000) // 밀리초 단위 (1시간 = 3600000 밀리초)
    @Scheduled(fixedRate = 60000)
    public void performTask() {
        // 실행할 작업을 여기에 작성합니다.
        System.out.println("주기적인 작업을 수행합니다.");
    }
}