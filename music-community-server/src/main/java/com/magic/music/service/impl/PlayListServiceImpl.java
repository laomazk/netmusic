package com.magic.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magic.music.entity.PlayList;
import com.magic.music.entity.vo.RespPageBean;
import com.magic.music.mapper.PlayListMapper;
import com.magic.music.service.PlayListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
@Slf4j
public class PlayListServiceImpl extends ServiceImpl<PlayListMapper, PlayList> implements PlayListService {

    @Autowired
    PlayListMapper playListMapper;

    @Override
    public RespPageBean getPlayListByPage(Integer page, Integer size, String style) {
        IPage<PlayList> iPage = new Page<>(page, size);
        QueryWrapper<PlayList> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(style)){
            queryWrapper.eq("style",style);
            log.info("-------[{}]------",style);
        }
        IPage<PlayList> playListIPage = playListMapper.selectPage(iPage, queryWrapper);
        List<PlayList> data = playListIPage.getRecords();
        long total = playListIPage.getTotal();

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(data)
                .setTotal(total);

        return respPageBean;
    }
}
