package cn.xts.demo;

import cn.xts.demo.config.TimedTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {


    @Bean
    public TimedTask getTask() {
        return new TimedTask();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }


}