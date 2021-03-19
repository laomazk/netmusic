package com.magic.music.service;

import com.magic.music.entity.PlayList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.music.entity.vo.RespPageBean;

/**
 * <p>
 * 歌单表 服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface PlayListService extends IService<PlayList> {

    RespPageBean getPlayListByPage(Integer page, Integer size, String style);
}
