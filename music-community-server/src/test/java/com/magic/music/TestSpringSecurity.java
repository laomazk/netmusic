package com.magic.music;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author laoma
 * @create 2021-03-09 20:35
 */
@SpringBootTest
public class TestSpringSecurity {

    @Test
    void test1() {
        String encode = new BCryptPasswordEncoder().encode("123");
        System.out.println("encode = " + encode);
        boolean matches = new BCryptPasswordEncoder().matches("123", encode);
        System.out.println(matches);
    }

    @Test
    void test2() {

    }
}
