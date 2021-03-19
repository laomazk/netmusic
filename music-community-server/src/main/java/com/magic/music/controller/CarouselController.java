package com.magic.music.controller;


import com.magic.music.entity.Carousel;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/sou/carousel")
public class CarouselController {
    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Autowired
    private CarouselService carouselService;

    /**
     * 获取所有的轮播图
     */
    @GetMapping("/")
    public List<Carousel> getAllCarousel() {
        return carouselService.getAllCarousel();
    }

    /**
     * 根据id更新轮播图图片
     */
    @PostMapping("/updatepic")
    public RespBean updateCarousel(MultipartFile file, Integer id) {
        return carouselService.updateCarousel(file, id);
    }

    /**
     * 更新轮播图信息
     */
    @PutMapping("/put/")
    public RespBean updateInfo(@RequestBody Carousel carousel) {
        return carouselService.updateInfo(carousel);
    }
}

