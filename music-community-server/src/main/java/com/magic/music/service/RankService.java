package com.magic.music.service;

import com.magic.music.entity.Rank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评价 服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface RankService extends IService<Rank> {

    Integer getSongListRankById(Integer id);

    Integer getScoreSum(Integer id);
}
