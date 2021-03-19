package com.magic.music;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magic.music.entity.Listener;
import com.magic.music.entity.PlayList;
import com.magic.music.mapper.ListenerMapper;
import com.magic.music.service.CarouselService;
import com.magic.music.service.PlayListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author laoma
 * @create 2021-03-09 21:54
 */
@SpringBootTest
public class TestMP {

    @Autowired
    ListenerMapper listenerMapper;

    @Autowired
    CarouselService carouselService;

    @Autowired
    PlayListService playListService;

    @Test
    void test() {
        QueryWrapper<Listener> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("password","id").eq("id",22);
        List<Listener> listeners = listenerMapper.selectList(queryWrapper);
        Listener listener = listenerMapper.selectOne(queryWrapper);
        System.out.println("listener = " + listener);
        System.out.println("listeners = " + listeners);
    }

    @Test
    void checkCor() {
        carouselService.list().forEach(item ->{
            System.out.println(item);
        });
    }

    @Test
    void testPage() {
        IPage<PlayList> iPage = new Page<>(1,2);
        IPage<PlayList> page = playListService.page(iPage);
        List<PlayList> records = page.getRecords();
        long pages = page.getPages();
        System.out.println("records = " + records);
        System.out.println("page.getPages() = " + page.getPages());
    }
}
