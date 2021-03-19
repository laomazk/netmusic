package com.magic.music.mapper;

import com.magic.music.entity.Listener;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.magic.music.entity.Role;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface ListenerMapper extends BaseMapper<Listener> {

    List<Role> getListenerRolesById(Integer id);
}
