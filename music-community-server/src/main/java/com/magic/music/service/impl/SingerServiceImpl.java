package com.magic.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magic.music.entity.Singer;
import com.magic.music.entity.vo.RespPageBean;
import com.magic.music.mapper.SingerMapper;
import com.magic.music.service.SingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 歌手 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
@Slf4j
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    SingerMapper singerMapper;
    /**
     * 分页查询歌手
     * @return
     */
    @Override
    public RespPageBean getSingerByPage(Integer page, Integer size, String type) {
        IPage<Singer> iPage = new Page<>(page,size);
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        if(!type.equals("-1")){
            queryWrapper.eq("sex",Byte.parseByte(type));
            log.info("-------[{}]------",type);
        }
        IPage<Singer> singerIPage = singerMapper.selectPage(iPage, queryWrapper);
        List<Singer> data = singerIPage.getRecords();
        long total = singerIPage.getTotal();
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(data)
                .setTotal(total);
        return respPageBean;
    }
}
