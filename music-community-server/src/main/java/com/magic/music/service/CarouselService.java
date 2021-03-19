package com.magic.music.service;

import com.magic.music.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.music.entity.vo.RespBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 轮播图表 服务类
 * </p>
 *
 * @author magic
 * @since 2021-03-11
 */
public interface CarouselService extends IService<Carousel> {

    List<Carousel> getAllCarousel();

    RespBean updateCarousel(MultipartFile file, Integer id);

    RespBean updateInfo(Carousel carousel);
}
