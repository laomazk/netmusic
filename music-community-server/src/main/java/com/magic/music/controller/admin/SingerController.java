package com.magic.music.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.music.entity.Listener;
import com.magic.music.entity.Singer;
import com.magic.music.service.SingerService;
import com.magic.music.utils.FastDFSUtils;
import com.magic.music.entity.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/source/singer")
public class SingerController {
    @Autowired
    SingerService singerService;

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${listener.singer.default.pic}")
    String defaultPic;

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Singer singer) {
        singer.setPic(defaultPic);
        if (singerService.save(singer)) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Singer singer) {
        if (singerService.updateById(singer)) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {
        Singer singer = singerService.getById(id);
        String oldUrl = singer.getPic();
        if (oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultPic, oldUrl)) {
            String oldFileId = oldUrl.substring(nginxHost.length(), oldUrl.length());
            System.out.println("oldFileId = " + oldFileId);
            FastDFSUtils.delete(oldFileId);
        }
        if (singerService.removeById(id)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 查询所有歌手
     */
    @GetMapping("/")
    public List<Singer> allSinger() {
        return singerService.list();
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
    public Object singerOfSex(@PathVariable String sex) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", Integer.parseInt(sex));
        return singerService.list(queryWrapper);
    }

    /**
     * 更新歌手图片
     */
    @PostMapping("/updatepic")
    public RespBean updateSingerPic(MultipartFile file, Integer id) {
        if (file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png")) {
            String fileId = FastDFSUtils.upload(file);
            String url = nginxHost + fileId;

            // 拿到旧的url取出fileId,删掉远程的file
            Singer singer = singerService.getById(id);
            String oldUrl = singer.getPic();
            if (oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultPic, oldUrl)) {
                String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
                FastDFSUtils.delete(oldFileId);
            }
            singer.setPic(url);
            if (singerService.updateById(singer)) {
                return RespBean.ok("更新成功", url);
            }
            return RespBean.error("更新失败");
        }
        return RespBean.error("更新失败","图片格式应为[jpg][png]");
    }

}

