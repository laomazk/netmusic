package com.magic.music.controller.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Singer;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.entity.vo.RespPageBean;
import com.magic.music.service.SingerService;
import com.magic.music.utils.FastDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌手 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@Slf4j
@RequestMapping("/user/singer")
public class UserSingerController {
    @Autowired
    SingerService singerService;

    // /**
    //  * 查询所有歌手
    //  */
    // @GetMapping("/")
    // public List<Singer> allSinger() {
    //     return singerService.list();
    // }

    /**
     * 分页查询所有歌手
     */
    @GetMapping("/")
    public RespPageBean getSingerByPage(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(defaultValue = "-1") String type){
        return singerService.getSingerByPage(page,size,type);
    }

    /**
     * 根据歌手名字模糊查询列表
     */
    @GetMapping(value = "/singerOfName/{name}")
    public List<Singer> singerOfName(@PathVariable String name) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return singerService.list(queryWrapper);
    }

    /**
     * 根据性别查询
     */
    @GetMapping(value = "/singerOfSex/{sex}")
    public List<Singer> singerOfSex(@PathVariable String sex) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", Integer.parseInt(sex));
        return singerService.list(queryWrapper);
    }

    /**
     * 根据歌手id查询歌手信息
     */
    @GetMapping("/id/{id}")
    public Singer getSingerById(@PathVariable Integer id){
        return singerService.getById(id);
    }

}

