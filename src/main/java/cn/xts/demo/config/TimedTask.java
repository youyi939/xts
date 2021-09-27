package cn.xts.demo.config;


import cn.xts.demo.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */

@Component
public class TimedTask {

    @Autowired
    MembersService membersService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetIntegral() {
        membersService.resetIntegral();
    }
}
