package com.magic.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Song;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.SongService;
import com.magic.music.utils.FastDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 歌曲 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user/song/")
@Slf4j
public class UserSongController {

    @Autowired
    private SongService songService;

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${song.default.pic}")
    String defaultSongPic;


    /**
     * 根据歌手ID查询歌曲
     */
    @GetMapping("/id/{id}")
    public List<Song> findSongBySingerId(@PathVariable Integer id) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", id);
        return songService.list(queryWrapper);
    }

    /**
     * 查询所有歌曲
     */
    @GetMapping("/")
    public List<Song> findSong() {
        return songService.list();
    }

    /**
     * 根据歌手名称查询歌曲
     */
    @GetMapping("/name/{name}")
    public List<Song> findSongBySingerName(@PathVariable String name) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return songService.list(queryWrapper);
    }



    /**
     * 根据歌曲id查询歌曲
     */
    @GetMapping("/detail/{id}")
    public Song getSongDetail(@PathVariable Integer id) {
        return songService.getById(id);
    }


    /**
     *根据歌手名字模糊查询歌曲
     */
    @GetMapping("/like/{name}")
    public List<Song> getSongLikeName(@PathVariable String name){
        return songService.list(new QueryWrapper<Song>().like("name",name));
    }


    /**
     * 下载歌曲
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<byte []> downloadMusic(@PathVariable Integer id){
        return songService.downloadMusic(id);
    }

}

