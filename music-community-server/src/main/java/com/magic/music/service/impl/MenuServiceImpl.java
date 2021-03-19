package com.magic.music.service.impl;

import com.magic.music.entity.Listener;
import com.magic.music.entity.Menu;
import com.magic.music.mapper.MenuMapper;
import com.magic.music.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    // @Cacheable(cacheNames = "menu",keyGenerator = "myKeyGenerator")
    @Override
    public List<Menu> getAllMenusWithRole() {
        // log.info("根据用户id查询menu的方法被调用");
        return menuMapper.getAllMenusWithRole();
    }

    // 标记方法之上，表示该方法的返回结果需要缓存
    @Cacheable(cacheNames = "menu")
    @Override
    public List<Menu> getMenuByListenerId(Integer id) {
        log.info("根据用户id查询menu的方法被调用");
        return menuMapper.getMenuByListenerId(id);
    }
}
