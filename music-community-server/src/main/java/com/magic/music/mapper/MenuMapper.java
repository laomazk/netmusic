package com.magic.music.mapper;

import com.magic.music.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getAllMenusWithRole();

    List<Menu> getMenuByListenerId(@Param("lid") Integer lid);

    List<Menu> getAllMenus();

    List<Integer> getMidsByRid(Integer rid);
}
