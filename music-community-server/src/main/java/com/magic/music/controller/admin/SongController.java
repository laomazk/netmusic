package com.magic.music.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Song;
import com.magic.music.service.SongService;
import com.magic.music.utils.FastDFSUtils;
import com.magic.music.entity.vo.RespBean;
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
@RequestMapping("/source/song")
@Slf4j
public class SongController {

    @Autowired
    private SongService songService;

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${song.default.pic}")
    String defaultSongPic;

    /**
     * 添加歌曲
     */
    @PostMapping("/add")
    public RespBean addSong(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        //获取前端传来的参数 所属歌手id 歌名 专辑 默认图片 歌词
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String pic = defaultSongPic;
        String lyric = request.getParameter("lyric").trim();

        // 上传歌曲文件
        if (file.isEmpty()) {
            return RespBean.error("上传文件为空，歌曲上传失败");
        }
        System.out.println(file.getOriginalFilename());
        if (!file.getOriginalFilename().endsWith(".mp3") && !file.getOriginalFilename().endsWith(".flac")) {
            // 浏览器不支持flac格式的歌曲，需要另外解析
            return RespBean.error("上传歌曲格式有误");
        }
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost + fileId;
        Song song = new Song();
        song.setSingerId(Integer.parseInt(singerId))
                .setName(name)
                .setIntroduction(introduction)
                .setPic(pic)
                .setLyric(lyric)
                .setUrl(url);
        if (songService.save(song)) {
            return RespBean.ok("添加成功");
        }

        return RespBean.error("添加失败");
    }

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
     * 修改歌曲
     */
    @PutMapping("/")
    public RespBean updateSong(@RequestBody Song song) {
        if (songService.updateById(song)) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    /**
     * 删除歌曲
     */
    @DeleteMapping("/{id}")
    public RespBean deleteSongById(@PathVariable Integer id) {
        Song song = songService.getById(id);
        String url = song.getUrl();
        String pic = song.getPic();
        if (url != null && url.length() > 0) {
            String oldFileId = url.substring(nginxHost.length(), url.length());
            FastDFSUtils.delete(oldFileId);
        }
        if(pic!=null && pic.length() > 0 && !StringUtils.equals(defaultSongPic, pic)){
            String oldFileId = url.substring(nginxHost.length(), pic.length());
            FastDFSUtils.delete(oldFileId);
        }
        if (songService.removeById(id)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 更新歌曲图片
     */
    @PostMapping("/updatepic")
    public RespBean updateSongPic(MultipartFile file, Integer id) {
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost + fileId;

        // 拿到旧的url取出fileId,删掉远程的file
        Song song = songService.getById(id);
        String oldUrl = song.getPic();
        if ( oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultSongPic, oldUrl) && oldUrl.startsWith(nginxHost)) {

            String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
            FastDFSUtils.delete(oldFileId);
        }
        song.setPic(url);
        if (songService.updateById(song)) {
            return RespBean.ok("更新成功", url);
        }
        return RespBean.error("更新失败");
    }

    /**
     * 更新歌曲
     */
    @PostMapping("/updateurl")
    public RespBean updateSongUrl(MultipartFile file, Integer id) {
        return songService.updateSongUrl(file, id);
    }

    /**
     * 根据歌曲id查询歌曲
     */
    @GetMapping("/detail/{id}")
    public Song getSongDetail(@PathVariable Integer id) {
        return songService.getById(id);
    }

    // /**
    //  *根据歌手名字精确查询歌曲
    //  */
    // @GetMapping("/name/{songFullName}")
    // public List<Song> getSongBySongFullName(@PathVariable String songFullName){
    //     return songService.list(new QueryWrapper<Song>().eq("name",songFullName));
    // }

    /**
     *根据歌手名字模糊查询歌曲
     */
    @GetMapping("/like/{name}")
    public List<Song> getSongLikeName(@PathVariable String name){
        return songService.list(new QueryWrapper<Song>().like("name",name));
    }




}

