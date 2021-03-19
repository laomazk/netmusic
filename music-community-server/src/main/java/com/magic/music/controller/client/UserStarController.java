package com.magic.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Star;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.StarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收藏表 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user/star/")
public class UserStarController {

    @Autowired
    private StarService starService;

    /**
     * 收藏类型(0歌曲1歌单)
     * 添加收藏
     */
    @PostMapping("/")
    public RespBean addStar(@RequestBody Star star) {
        if (star.getType() == 0) {
            if(star.getSongId()==null) {
                return RespBean.error("收藏歌曲为空");
            }
            if(starService.existSong(star)){
                return RespBean.error("您已经收藏了");
            }
        }
        if (star.getType() == 1 ) {
            if(star.getPlayListId()==null){
                return RespBean.error("收藏歌单为空");
            }
            if(starService.existPlayList(star)){
                return RespBean.error("您已经收藏了");
            }
        }
        if(starService.save(star)){
            return RespBean.ok("收藏成功");
        }
        return RespBean.error("收藏失败");
    }

    /**
     * 删除收藏
     */
    @DeleteMapping("/{id}")
    public RespBean deleteStar(@PathVariable Integer id){
        if(starService.removeById(id)){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 查询所有收藏
     */
    @GetMapping("/")
    public List<Star> selectAllStar(){
        return starService.list();
    }

    /**
     * 查询某个用户的收藏列表
     */
    @GetMapping("/{id}")
    public List<Star> selectStarByUser(@PathVariable Integer id){
        return starService.list(new QueryWrapper<Star>().eq("user_id",id));
    }

    /**
     * 检查某个歌单是否被收藏 true收藏 false 没收藏
     */
    @GetMapping("/check/")
    public Boolean checkStarOfPlayList(@RequestParam Integer userId,@RequestParam Integer playListId){
        return starService.checkStarOfPlayList(userId,playListId);
    }

}

