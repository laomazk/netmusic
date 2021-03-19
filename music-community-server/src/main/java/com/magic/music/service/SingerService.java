package com.magic.music.service;

import com.magic.music.entity.Singer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.music.entity.vo.RespPageBean;

/**
 * <p>
 * 歌手 服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface SingerService extends IService<Singer> {

    RespPageBean getSingerByPage(Integer page, Integer size, String type);
}
