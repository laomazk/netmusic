package com.magic.music.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.PlayList;
import com.magic.music.entity.Song;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.PlayListService;
import com.magic.music.service.SongService;
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
@RequestMapping("/source/playlist/")
public class PlayListController {

    @Autowired
    PlayListService playListService;


    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${song.default.pic}")
    String defaultSongPic;

    /**
     * 添加歌单
     */
    @PostMapping("/")
    public RespBean addPlayList(@RequestBody PlayList playList){
        playList.setPic(defaultSongPic);
        if(playListService.save(playList)){
            return RespBean.ok("添加歌单成功");
        }
        return RespBean.error("添加失败");
    }
    /**
     * 修改歌单
     */
    @PutMapping("/")
    public RespBean updatePlayList(@RequestBody PlayList playList){
        if(playListService.updateById(playList)){
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePlayListById(@PathVariable Integer id){
        // 删除图片
        // 拿到旧的url取出fileId,删掉远程的file
        PlayList playList = playListService.getById(id);
        String oldUrl = playList.getPic();
        if (oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultSongPic, oldUrl)) {

            String oldFileId = oldUrl.substring(nginxHost.length(), oldUrl.length());
            FastDFSUtils.delete(oldFileId);
        }
        if(playListService.removeById(id)){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/id/{id}")
    public PlayList selectByPrimaryId(@PathVariable Integer id){
        return playListService.getById(id);
    }

    /**
     * 查询所有歌单
     */
    @GetMapping("/")
    public List<PlayList> findAllList(){
        return playListService.list();
    }

    /**
     * 根据标题精确查询到歌单列表
     * @return
     */
    @GetMapping("/title/{title}")
    public List<PlayList> selectByTitle(@PathVariable String title){
        return playListService.list(new QueryWrapper<PlayList>().eq("title",title));
    }

    /**
     * 根据标题模糊查询歌单列表
     */
    @GetMapping("/liketitle/{title}")
    public List<PlayList> selectLikeTitle(@PathVariable String title){
        return playListService.list(new QueryWrapper<PlayList>().like("title",title));
    }

    /**
     * 根据风格模糊查询歌单列表
     */
    @GetMapping("/likestyle/{style}")
    public List<PlayList> selectLikeStyle(@PathVariable String style){
        return playListService.list(new QueryWrapper<PlayList>().like("style",style));
    }

    /**
     * 更新歌单图片
     */
    @PostMapping("/updatepic")
    public RespBean updatePlayListPic(MultipartFile file, Integer id) {
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost + fileId;

        // 拿到旧的url取出fileId,删掉远程的file
        PlayList playList = playListService.getById(id);
        String oldUrl = playList.getPic();
        if (oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultSongPic, oldUrl)) {

            String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
            FastDFSUtils.delete(oldFileId);
        }
        playList.setPic(url);
        if (playListService.updateById(playList)) {
            return RespBean.ok("更新成功", url);
        }
        return RespBean.error("更新失败");
    }

}

