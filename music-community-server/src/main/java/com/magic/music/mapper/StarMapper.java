package com.magic.music.mapper;

import com.magic.music.entity.Star;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 收藏表 Mapper 接口
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface StarMapper extends BaseMapper<Star> {

    Integer existSong(Integer userId, Integer songId);

    Integer existPlayList(Integer userId, Integer playListId);
}
