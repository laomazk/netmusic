package com.magic.music.service.impl;

import com.magic.music.entity.Rank;
import com.magic.music.mapper.RankMapper;
import com.magic.music.service.RankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评价 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
public class RankServiceImpl extends ServiceImpl<RankMapper, Rank> implements RankService {

    @Autowired
    private RankMapper rankMapper;

    /**
     * 计算平均分
     *
     * @param id
     * @return
     */
    @Override
    public Integer getSongListRankById(Integer id) {
        // 计算总人数
        int rankNum = super.count();
        if (rankNum == 0) {
            return 5;
        }

        return rankMapper.getScoreSum(id) / rankNum;
    }

    /**
     * 查总分
     */
    @Override
    public Integer getScoreSum(Integer id) {
        return rankMapper.getScoreSum(id);
    }


}
