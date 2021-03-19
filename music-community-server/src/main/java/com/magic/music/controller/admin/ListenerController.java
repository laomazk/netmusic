package com.magic.music.controller.admin;


import com.magic.music.entity.Listener;
import com.magic.music.entity.Singer;
import com.magic.music.entity.vo.PassWd;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.ListenerService;
import com.magic.music.utils.FastDFSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author magic
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/listener/basic/")
public class ListenerController {
    @Autowired
    private ListenerService listenerService;

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${listener.singer.default.pic}")
    String defaultPic;

    /**
     * 添加前端用户
     */
    @PostMapping("/")
    public RespBean addListener(@RequestBody Listener listener) {
        if (listenerService.addListener(listener)) {
            return RespBean.ok("添加用户成功");
        }
        return RespBean.error("添加用户失败");
    }

    /**
     * 修改前端用户
     */
    @PutMapping("/")
    public RespBean updateListener(@RequestBody Listener listener) {
        // if (!StringUtils.isEmpty(listener.getPassword())) {
        //     listener.setPassword(new BCryptPasswordEncoder().encode(listener.getPassword()));
        // }
        if (listenerService.updateById(listener)) {
            return RespBean.ok("修改用户成功");
        }
        return RespBean.error("修改用户失败");
    }

    /**
     * 删除前端用户
     */
    @DeleteMapping("/{id}")
    public RespBean deleteListener(@PathVariable Integer id) {
        if (listenerService.removeById(id)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/id/{id}")
    public Listener selectByPrimaryKey(@PathVariable Integer id) {
        return listenerService.getById(id);
    }

    /**
     * 查询所有的前端用户
     */
    @GetMapping("/")
    public List<Listener> selectAllListener() {
        return listenerService.list();
    }

    /**
     * 更新前端用户图片
     */
    @PostMapping("/updatepic")
    public RespBean updateListenerrPic(MultipartFile file, Integer id) {
        if (file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png")) {
            String fileId = FastDFSUtils.upload(file);
            String url = nginxHost + fileId;

            // 拿到旧的url取出fileId,删掉远程的file
            Listener listener = listenerService.getById(id);
            String oldUrl = listener.getAvator();
            if (oldUrl != null && oldUrl.length() > 0 && !StringUtils.equals(defaultPic, oldUrl)) {
                String oldFileId = oldUrl.substring(nginxHost.length(), url.length());
                FastDFSUtils.delete(oldFileId);
            }
            listener.setAvator(url);
            if (listenerService.updateById(listener)) {
                return RespBean.ok("更新成功", url);
            }
            return RespBean.error("更新失败");
        }
        return RespBean.error("更新失败", "图片格式应为[jpg][png]");
    }

    /**
     * 验证前端传入的密码
     */
    @PostMapping("/updatepd")
    public RespBean updatePassword(@RequestBody PassWd passWd) {
        return listenerService.updatePassword(passWd);
    }

}

