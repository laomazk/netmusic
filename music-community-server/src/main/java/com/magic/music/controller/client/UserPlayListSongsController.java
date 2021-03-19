package com.magic.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.PlayListSongs;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.PlayListSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 歌单歌曲表	 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user/playlistsong/")
public class UserPlayListSongsController {

    @Autowired
    private PlayListSongsService playListSongsService;


    /**
     * 根据歌单id查询歌曲
     *
     * @return
     */
    @GetMapping("/{id}")
    public List<PlayListSongs> detail(@PathVariable Integer id) {
        return playListSongsService.list(new QueryWrapper<PlayListSongs>().eq("play_list_id", id));
    }

}

