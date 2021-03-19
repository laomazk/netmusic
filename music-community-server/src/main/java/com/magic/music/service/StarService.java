package com.magic.music.service;

import com.magic.music.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收藏表 服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface StarService extends IService<Star> {

    boolean existSong(Star star);

    boolean existPlayList(Star star);

    Boolean checkStarOfPlayList(Integer userId, Integer playListId);
}
