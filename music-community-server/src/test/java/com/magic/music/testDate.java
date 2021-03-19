package com.magic.music;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author laoma
 * @create 2021-02-27 11:05
 */
@SpringBootTest
public class testDate {

    @Test
    void test1() {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        System.out.println("new Date() = " + new Date());
    }

    @Test
    void testTree() {
        TreeMap<String ,Integer> treeMap = new TreeMap();
        treeMap.put("2020-02-16",1);
        treeMap.put("2020-02-18",1);
        treeMap.put("2020-02-15",1);
        System.out.println(treeMap);
        // Set<Map.Entry<String, Integer>> entrySet = treeMap.entrySet();
        // for (Map.Entry<String, Integer> entry:entrySet) {
        //     System.out.println(entry);
        // }
        treeMap.forEach((k,v)->{
            System.out.println("k = " + k+"v = " + v);

        });
    }
}
