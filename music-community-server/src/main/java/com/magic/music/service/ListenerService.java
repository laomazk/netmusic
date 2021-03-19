package com.magic.music.service;

import com.magic.music.entity.Listener;
import com.baomidou.mybatisplus.extension.service.IService;
import com.magic.music.entity.vo.PassWd;
import com.magic.music.entity.vo.RegListener;
import com.magic.music.entity.vo.RespBean;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface ListenerService extends IService<Listener> {

    boolean addListener(Listener listener);

    RespBean updatePassword(PassWd passWd);

    RespBean regListener(RegListener regListener);

}
