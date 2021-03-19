package com.magic.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.PlayList;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.entity.vo.RespPageBean;
import com.magic.music.service.PlayListService;
import com.magic.music.utils.FastDFSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌单表 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user/playlist/")
public class UserPlayListController {

    @Autowired
    PlayListService playListService;


    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/id/{id}")
    public PlayList selectByPrimaryId(@PathVariable Integer id) {
        return playListService.getById(id);
    }

    // /**
    //  * 查询所有歌单
    //  */
    // @GetMapping("/")
    // public List<PlayList> findAllList() {
    //     return playListService.list();
    // }

    /**
     * 分页查询所有歌单
     */
    @GetMapping("/")
    public RespPageBean getPlayListByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(defaultValue = "") String style) {
        return playListService.getPlayListByPage(page,size,style);
    }

    /**
     * 根据标题精确查询到歌单列表
     *
     * @return
     */
    @GetMapping("/title/{title}")
    public List<PlayList> selectByTitle(@PathVariable String title) {
        return playListService.list(new QueryWrapper<PlayList>().eq("title", title));
    }

    /**
     * 根据标题模糊查询歌单列表
     */
    @GetMapping("/liketitle/{title}")
    public List<PlayList> selectLikeTitle(@PathVariable String title) {
        return playListService.list(new QueryWrapper<PlayList>().like("title", title));
    }

    /**
     * 根据风格模糊查询歌单列表
     */
    @GetMapping("/likestyle/{style}")
    public List<PlayList> selectLikeStyle(@PathVariable String style) {
        return playListService.list(new QueryWrapper<PlayList>().like("style", style));
    }

}

