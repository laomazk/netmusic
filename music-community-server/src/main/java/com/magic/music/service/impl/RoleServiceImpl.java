package com.magic.music.service.impl;

import com.magic.music.entity.Role;
import com.magic.music.mapper.RoleMapper;
import com.magic.music.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
