package com.magic.music.service.impl;

import cn.hutool.http.server.action.RootAction;
import com.magic.music.entity.Carousel;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.mapper.CarouselMapper;
import com.magic.music.service.CarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.magic.music.utils.FastDFSUtils;
import com.magic.music.utils.MyKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 轮播图表 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-03-11
 */
@Service
@Slf4j
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${redis.carousel.key}")
    String carouselKey;

    @Autowired
    RedisTemplate redisTemplate;

    // 标记方法之上，表示该方法的返回结果需要缓存
    // @Cacheable(cacheNames = "carousel",key = "#root.targetClass")
    @Override
    public List<Carousel> getAllCarousel() {
        List<Carousel> carouselList = null;
        if (redisTemplate.opsForValue().get(carouselKey) == null) {
            carouselList = super.list();
            redisTemplate.opsForValue().set(carouselKey, carouselList);
            return carouselList;
        }
        carouselList = (List<Carousel>) redisTemplate.opsForValue().get(carouselKey);
        return carouselList;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public RespBean updateCarousel(MultipartFile file, Integer id) {
        if (file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png")) {
            log.info("当前对象[{}]", this);
            String fileId = FastDFSUtils.upload(file);
            String url = nginxHost + fileId;
            Carousel carousel = super.getById(id);
            String oldUrl = carousel.getPic();
            if (oldUrl != null && oldUrl.length() > 0) {
                String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
                FastDFSUtils.delete(oldFileId);
            }
            // 删除缓存中的轮播图
            deleteCatchOfCarousel();
            carousel.setPic(url);
            if (super.updateById(carousel)) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("更新失败");
        }
        return RespBean.error("更新失败", "图片格式应为[jpg][png]");
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public RespBean updateInfo(Carousel carousel) {
        // 删除缓存信息
        deleteCatchOfCarousel();
        if(super.updateById(carousel)){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    public void deleteCatchOfCarousel() {
        if (redisTemplate.hasKey(carouselKey)) {
            redisTemplate.delete(carouselKey);
        }
    }
}
