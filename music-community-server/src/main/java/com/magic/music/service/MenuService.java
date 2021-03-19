package com.magic.music.service;

import com.magic.music.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getAllMenusWithRole();

    List<Menu> getMenuByListenerId(Integer id);
}
