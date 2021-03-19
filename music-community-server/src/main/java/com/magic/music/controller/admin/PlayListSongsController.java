package com.magic.music.controller.admin;


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
@RequestMapping("/source/playlist/songs/")
public class PlayListSongsController {

    @Autowired
    private PlayListSongsService playListSongsService;

    /**
     * 给歌单添加歌曲
     */
    @PostMapping("/")
    public RespBean addListSong(HttpServletRequest request) {
        // 获取前端传过来的参数 歌曲id 歌单id
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        PlayListSongs playListSongs = new PlayListSongs();
        playListSongs.setSongId(Integer.parseInt(songId))
                .setPlayListId(Integer.parseInt(songListId));
        if (playListSongsService.save(playListSongs)) {
            return RespBean.ok("保存成功");
        }
        return RespBean.error("保存失败");
    }

    /**
     * 根据歌单id查询歌曲
     *
     * @return
     */
    @GetMapping("/{id}")
    public List<PlayListSongs> detail(@PathVariable Integer id) {
        return playListSongsService.list(new QueryWrapper<PlayListSongs>().eq("play_list_id", id));
    }

    /**
     * 删除歌单里的歌曲
     */
    @DeleteMapping("/")
    public RespBean delete(HttpServletRequest request) {
        //歌曲id 歌单id
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        boolean flag = playListSongsService.remove(new QueryWrapper<PlayListSongs>()
                .eq("play_list_id", songListId)
                .eq("song_id", songId));
        if(flag){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

