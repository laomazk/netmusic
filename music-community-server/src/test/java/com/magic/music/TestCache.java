package com.magic.music;

import com.magic.music.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author laoma
 * @create 2021-02-28 14:54
 */
@SpringBootTest
public class TestCache {
    @Autowired
    MenuService menuService;

    @Test
    void testCache() {
        for (int i = 0; i < 3; i++) {
            menuService.getAllMenusWithRole();
            System.out.println("i = " + i);
        }
    }
}
