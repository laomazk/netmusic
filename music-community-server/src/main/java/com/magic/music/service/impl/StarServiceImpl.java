package com.magic.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Star;
import com.magic.music.mapper.StarMapper;
import com.magic.music.service.StarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {

    @Autowired
    private StarMapper starMapper;

    @Override
    public boolean existSong(Star star) {
        Integer userId = star.getUserId();
        Integer songId = star.getSongId();
        return starMapper.existSong(userId, songId) > 0;
    }

    @Override
    public boolean existPlayList(Star star) {
        Integer userId = star.getUserId();
        Integer playListId = star.getPlayListId();
        return starMapper.existPlayList(userId, playListId) > 0;
    }

    @Override
    public Boolean checkStarOfPlayList(Integer userId, Integer playListId) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId).eq("play_list_id",playListId);
        List<Star> starList = super.list(queryWrapper);
        return starList.size() > 0;
    }
}
