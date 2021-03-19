package com.magic.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@MapperScan("com.magic.music.mapper")
@RestController
@SpringBootApplication
@EnableCaching //开启缓存功能
public class MusicCommunityServerApplication {

    @GetMapping("/aa")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(MusicCommunityServerApplication.class, args);
    }

}
