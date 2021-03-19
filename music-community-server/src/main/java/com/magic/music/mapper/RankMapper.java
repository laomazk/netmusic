package com.magic.music.mapper;

import com.magic.music.entity.Rank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 评价 Mapper 接口
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface RankMapper extends BaseMapper<Rank> {

    Integer getScoreSum(Integer id);
}
