package com.magic.music;

import com.magic.music.service.ListenerService;
import com.magic.music.service.impl.ListenerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class MusicCommunityServerApplicationTests {

    @Autowired
    ListenerServiceImpl listenerService;

    @Test
    void contextLoads() {
    }

    @Test
    void testloadUserByUsername (){
        // UserDetails aa = listenerService.loadUserByUsername("aa");
        // System.out.println("aa = " + aa);
        UserDetails 系统管理员 = listenerService.loadUserByUsername("系统管理员");
        System.out.println("系统管理员 = " + 系统管理员);
    }

}
