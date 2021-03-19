package com.magic.music.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laoma
 * @create 2021-02-15 12:14
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/admin/hello")
    public String hello2(){
        return "admin";
    }

    @GetMapping("/user/hello")
    public String hello3(){
        return "user";
    }
}
