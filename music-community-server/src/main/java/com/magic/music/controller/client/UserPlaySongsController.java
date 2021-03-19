package com.magic.music.controller.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.PlayListSongs;
import com.magic.music.service.PlayListSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author laoma
 * @create 2021-03-15 10:27
 */
@RestController
@RequestMapping("/user/playlist/songs/")
public class UserPlaySongsController {

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
