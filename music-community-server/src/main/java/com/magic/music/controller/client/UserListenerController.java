package com.magic.music.controller.client;


import com.magic.music.entity.Listener;
import com.magic.music.entity.vo.PassWd;
import com.magic.music.entity.vo.RegListener;
import com.magic.music.entity.vo.RespBean;
import com.magic.music.service.ListenerService;
import com.magic.music.utils.FastDFSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/user/listener/")
public class UserListenerController {
    @Autowired
    private ListenerService listenerService;

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @Value("${listener.singer.default.pic}")
    String defaultPic;

    /**
     * 注册
     */
    @PostMapping("/")
    public RespBean addListener(@RequestBody RegListener regListener) {
        return listenerService.regListener(regListener);
    }

    /**
     * 修改前端用户
     */
    @PutMapping("/")
    public RespBean updateListener(@RequestBody Listener listener) {
        if (listenerService.updateById(listener)) {
            return RespBean.ok("修改用户成功");
        }
        return RespBean.error("修改用户失败");
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

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/id/{id}")
    public Listener selectByPrimaryKey(@PathVariable Integer id) {
        return listenerService.getById(id);
    }
}

